package com.example.think.androidfun.net.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by iwm on 16/6/8.
 */
public abstract class BitmapCallback extends WMCallback {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public abstract void onCallCancel(Call call);

    public abstract void onCallSuccess(Call call, Response response, Bitmap bitmap);

    public abstract void onCallFailure(Call call, IOException e);

    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
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
        final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    onCallFailure(call, new IOException("Unexpected code from onResponse" + response));
                } else {
                    onCallSuccess(call, response, bitmap);
                }
            }
        });
    }
}
