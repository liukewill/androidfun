package com.example.think.androidfun.net.dns;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iwm on 2016/5/30.
 * getIPByHost中异步请求获取ip地址，获取后存储在内存中HashMap
 * 为了避免同步请求等待时间过长，首次返回ip地址为null
 */
public class HttpDNSUtil {

    private static long CACHE_MAX_SIZE = 5 * 1024 * 1024;

    public static String HTTPDNS_ENTRY = "180.76.76.112";

    private static HashMap<String, List<String>> mHostIpMap = new HashMap<>();

    public static String getIpUrl(String url, String host, String ip) {
        if (url == null || host == null || ip == null) {
            return url;
        }
        String ipUrl = url.replaceFirst(host, ip);
        return ipUrl;
    }

    public static List<String> getIPByHost(Context context, String host, long cacheTime) {
        updateIPByHost(context, host, cacheTime);
        List<String> res = null;
        if (!TextUtils.isEmpty(host)) {
            synchronized (mHostIpMap) {
                res = mHostIpMap.get(host);
            }
        }
        return res;
    }

    public static void updateIPByHost(Context context, final String host, long cacheTime) {
        if (TextUtils.isEmpty(host)) {
            return;
        }
        OkHttpClient okHttpClient;
        if(initCache(context,"CACHE_DNS")!=null) {//缓存目录不为空 使用缓存
            Cache cache = new Cache(initCache(context, "CACHE_DNS"), CACHE_MAX_SIZE);
            okHttpClient = new OkHttpClient().newBuilder()
                    .addNetworkInterceptor(new CacheInterceptor(cacheTime))
                    .cache(cache)
                    .build();
        }else{//缓存目录为空 不使用缓存
            okHttpClient = new OkHttpClient().newBuilder()
                    .addNetworkInterceptor(new CacheInterceptor(cacheTime))
                    .build();
        }

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(HTTPDNS_ENTRY)
                .addQueryParameter("dn", host)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                String[] resultArr = null;
                if (response.isSuccessful()) {
                    try {
                        String body = response.body().string();
                        resultArr = body.trim().split("\\s+");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (resultArr != null) {
                    synchronized (mHostIpMap) {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < resultArr.length; i++) {
                            if (isIp(resultArr[i])) {
                                list.add(resultArr[i]);
                            }
                        }
                        mHostIpMap.put(host, list);
                    }
                }
            }
        });
    }

    private static boolean isIp(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String rule = "^[0-9]*\\.[0-9]*\\.[0-9]*\\.[0-9]*$";
        Pattern pattern = Pattern.compile(rule);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    private static File initCache(Context context, String name) {
        if (context != null) {
            File baseDir = context.getCacheDir();
            if (baseDir != null) {
                File cacheDir = new File(baseDir, name);
                return cacheDir;
            }
        }
        return null;
    }
}
