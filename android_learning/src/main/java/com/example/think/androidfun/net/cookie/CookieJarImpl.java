package com.example.think.androidfun.net.cookie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by Ghost on 16/3/10.
 */

public class CookieJarImpl implements CookieJar {
    private final OkCookieManager cookieHandler;

    public CookieJarImpl(OkCookieManager cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        try {
            cookieHandler.put(cookies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> resultCookies = null;
        List<Cookie> rst = new ArrayList<Cookie>();
        try {
            resultCookies = cookieHandler.getAll();
            if (resultCookies == null) {
                resultCookies = new ArrayList<Cookie>();
            }

            for(Cookie cookie:resultCookies){
                if(cookie.matches(url)){
                    rst.add(cookie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rst;
    }
}