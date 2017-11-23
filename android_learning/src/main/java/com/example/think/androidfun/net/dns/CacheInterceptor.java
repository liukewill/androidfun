package com.example.think.androidfun.net.dns;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by iwm on 2016/5/30.
 */
public class CacheInterceptor implements Interceptor {

    private long mCacheTime = 1 * 60;

    public CacheInterceptor(long cacheTime) {
        this.mCacheTime = cacheTime;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + mCacheTime)
                .build();
    }
}
