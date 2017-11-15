package com.kenan.okhttp_client.net.dns;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iwm on 2016/5/30.
 */
public class HttpDNSInterceptor implements Interceptor {

    private Context context;
    private long mCacheTime = 1 * 60;

    public HttpDNSInterceptor(Context context, long cacheTime){
        this.context = context;
        this.mCacheTime = cacheTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        HttpUrl httpUrl = originRequest.url();

        String url = httpUrl.toString();
        String host = httpUrl.host();
        String hostIP = null;

        List<String> hostIpList = HttpDNSUtil.getIPByHost(context, host, mCacheTime);
        if(hostIpList != null && hostIpList.size() > 0) {
            hostIP = hostIpList.get(0);
        }

        Request.Builder builder = originRequest.newBuilder();
        if (hostIP != null) {
            String tmp = HttpDNSUtil.getIpUrl(url, host, hostIP);
            builder.url(tmp);
            builder.header("host", host);
        }
        Request newRequest = builder.build();
        Response newResponse = chain.proceed(newRequest);
        return newResponse;
    }
}
