package com.example.think.androidfun.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.lbs.comwmlib.net.callback.ProgressHelper;
import com.baidu.lbs.comwmlib.net.callback.ProgressRequestListener;
import com.baidu.lbs.comwmlib.net.callback.UIProgressRequestListener;
import com.baidu.lbs.comwmlib.net.dns.WMDNS;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by iwm on 16/6/12.
 */
public class WMOkHttpClient {

    private OkHttpClient mHttpClient;
    private final static ConcurrentHashMap<Object, Call> mHandlingCalls = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Object, Call> getHandlingCalls() {
        return mHandlingCalls;
    }


    public WMOkHttpClient(Context context) {
        this(context, null);
    }

    public WMOkHttpClient(Context context, OkHttpClient.Builder builder) {
        this(context, builder, true);
    }

    public WMOkHttpClient(Context context, OkHttpClient.Builder builder, boolean useHttpDns) {
        this(context, builder, useHttpDns, 10 * 60);
    }

    public WMOkHttpClient(Context context, OkHttpClient.Builder builder, boolean useHttpDns, long httpDnsCacheTime) {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }
        if (useHttpDns && !hasProxy()) {
            builder.dns(new WMDNS(context, httpDnsCacheTime));
        }
        mHttpClient = builder.build();
        mHandlingCalls.clear();
    }


    public Call sendRequest(String url, RequestParams getParams, RequestParams postParams, Callback callback) {
        return sendRequest(url, getParams, postParams, callback, null, false);
    }

    public Call sendRequest(String url, RequestParams getParams, RequestParams postParams, Callback callback, String tag, boolean isCancel) {
        return sendRequest(url, getParams, postParams, callback, tag, isCancel, (UIProgressRequestListener) null);
    }

    public Call sendRequest(String url, RequestParams getParams, RequestParams postParams, Callback callback, String tag, boolean isCancel, CacheControl cacheControl) {
        RequestBody requestBody = null;
        if (postParams != null) {
            requestBody = postParams.buildRequestBody();
        }
        return sendRequestBody(url, getParams, requestBody, callback, tag, isCancel, null, cacheControl);
    }

    public Call sendRequest(String url, RequestParams getParams, RequestParams postParams, Callback callback, String tag, boolean isCancel, UIProgressRequestListener uiProgressReqeustListener) {
        RequestBody requestBody = null;
        if (postParams != null) {
            requestBody = postParams.buildRequestBody();
        }
        return sendRequestBody(url, getParams, requestBody, callback, tag, isCancel, uiProgressReqeustListener, null);
    }

    public Call sendRequestBody(Request.Builder requestBuilder, Callback callback, String tag, boolean isCancel) {

        Request request = requestBuilder.tag(tag).build();

        Call call = mHttpClient.newCall(request);
        if (isCancel) {
            cancelCall(tag);
            mHandlingCalls.put(tag, call);
            Log.e("jsonCallback", "putting the Call " + tag + "---" + call);

        }
        call.enqueue(callback);
        return call;
    }


    private Call sendRequestBody(String url, RequestParams getParams, RequestBody requestBody, Callback callback, String tag, boolean isCancel,
                                 ProgressRequestListener uiProgressRequestListener, CacheControl cacheControl) {
        String queryUrl = getQueryUrl(url, getParams);

        Request.Builder builder = new Request.Builder();

        builder.url(queryUrl);

        if (requestBody != null) {
            if (uiProgressRequestListener != null) {
                builder.post(ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener));
            } else {
                builder.post(requestBody);
            }
        }

        Request request = null;
        if (cacheControl == null) {
            request = builder.tag(tag).build();
        } else {
            request = builder.tag(tag).cacheControl(cacheControl).build();
        }

        Call call = mHttpClient.newCall(request);
        if (isCancel) {
            cancelCall(tag);
            mHandlingCalls.put(tag, call);
            Log.e("jsonCallback", "putting the Call " + tag + "---" + call);
        }
        call.enqueue(callback);
        return call;
    }

    public void cancelAll() {
        mHttpClient.dispatcher().cancelAll();
    }

    private boolean hasProxy() {
        String proxyHost = System.getProperty("http.proxyHost");
        if (TextUtils.isEmpty(proxyHost)) {
            proxyHost = System.getProperty("https.proxyHost");
        }
        if (TextUtils.isEmpty(proxyHost)) {
            return false;
        }
        return true;
    }

    private String getQueryUrl(String url, RequestParams urlParams) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (urlParams != null) {
            HashMap<String, String> params = urlParams.getUrlParams();
            if (params != null && params.size() > 0) {
                Iterator iter = params.entrySet().iterator();
                boolean isFirstParams = true;
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String name = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    try {
                        if (isFirstParams) {
                            isFirstParams = false;
                        } else {
                            sb.append("&");
                        }
                        sb.append(URLEncoder.encode(name, "UTF-8"));
                        sb.append("=");
                        sb.append(URLEncoder.encode(value, "UTF-8"));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }

    public void cancelCall(String tag) {
        if (tag == null) {
            return;
        }


        for (Call call : mHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        if (mHandlingCalls.containsKey(tag)) {
            Call preCall = mHandlingCalls.get(tag);
            Log.e("cancelCall", "cancel the Call " + preCall.request().tag() + "---" + preCall);
            preCall.cancel();
        }

    }

}
