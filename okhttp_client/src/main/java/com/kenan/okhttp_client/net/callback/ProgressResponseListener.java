package com.kenan.okhttp_client.net.callback;

/**
 * Created by shanjie on 16/7/4.
 */
public interface ProgressResponseListener  {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
