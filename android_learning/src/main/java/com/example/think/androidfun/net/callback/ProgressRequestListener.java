package com.example.think.androidfun.net.callback;

/**
 * Created by shanjie on 16/7/4.
 */
public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
