package com.kenan.okhttp_client.net.callback;

import android.os.Handler;
import android.os.Looper;

import com.baidu.lbs.comwmlib.net.WMOkHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by iwm on 16/6/8.
 */
public abstract class JsonCallback extends WCallback {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public abstract void onCallCancel(Call call);

    public abstract void onCallSuccess(Call call, Response response, String responseString);

    public abstract void onCallFailure(Call call, IOException e);

    @Override
    public void onFailure(final Call call, final IOException e) {
        final Object tag = call.request().tag();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ConcurrentHashMap<Object, Call> mHandlingCalls = WMOkHttpClient.getHandlingCalls();
                if (mHandlingCalls != null && mHandlingCalls.containsKey(tag)) {
                    mHandlingCalls.remove(tag);
                }
                if (call.isCanceled()) {
                    onCallCancel(call);
                } else {
                    onCallFailure(call, e);
                }
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        //response.body().close();

        final String responseString = response.body().string();
        final Object tag = call.request().tag();
        mHandler.post(new Runnable() {
            @Override

            public void run() {
                ConcurrentHashMap<Object, Call> mHandlingCalls = WMOkHttpClient.getHandlingCalls();
                if (mHandlingCalls != null && mHandlingCalls.containsKey(tag)) {
                    mHandlingCalls.remove(tag);
                }

                if (call.isCanceled()) {
                    onCallCancel(call);
                } else if (response.code() != HttpURLConnection.HTTP_OK) {
                    onCallFailure(call, new IOException("Unexpected code from onResponse" + response));
                } else {
                    onCallSuccess(call, response, responseString);
                }
            }
        });
    }
}
