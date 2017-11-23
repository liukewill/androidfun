package com.example.think.androidfun.net.callback;

/**
 * Created by shanjie on 16/7/4.
 */
public interface ProgressResponseListener  {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
