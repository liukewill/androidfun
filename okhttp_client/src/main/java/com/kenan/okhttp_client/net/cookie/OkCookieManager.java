package com.kenan.okhttp_client.net.cookie;


import java.io.IOException;
import java.net.CookiePolicy;
import java.net.URI;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by Ghost on 16/3/10.
 */

public class OkCookieManager {
    private PersistentCookieStore store;

    private CookiePolicy policy;

    private static final String VERSION_ZERO_HEADER = "Set-cookie";

    private static final String VERSION_ONE_HEADER = "Set-cookie2";

    /**
     * Constructs a new cookie manager.
     * <p/>
     * The invocation of this constructor is the same as the invocation of
     * OkCookieManager(null, null).
     */
    public OkCookieManager() {
        this(null, null);
    }

    /**
     * Constructs a new cookie manager using a specified cookie store and a
     * cookie policy.
     *
     * @param store        a CookieStore to be used by cookie manager. The manager will
     *                     use a default one if the arg is null.
     * @param cookiePolicy a CookiePolicy to be used by cookie manager
     *                     ACCEPT_ORIGINAL_SERVER will be used if the arg is null.
     */
    public OkCookieManager(PersistentCookieStore store, CookiePolicy cookiePolicy) {
        this.store = store;
        policy = cookiePolicy == null ? CookiePolicy.ACCEPT_ORIGINAL_SERVER
                : cookiePolicy;
    }

    /**
     * Searches and gets all cookies in the cache by the specified uri in the
     * request header.
     *
     * @param uri the specified uri to search for
     *            <p/>
     *            a list of request headers
     * @return a map that record all such cookies, the map is unchangeable
     * @throws IOException if some error of I/O operation occurs
     */

    public List<Cookie> get(URI uri) throws IOException {
        if (uri == null) {
            throw new IllegalArgumentException();
        }
        List<Cookie> result = store.get(HttpUrl.get(uri));
        return result;
    }


    public List<Cookie> getAll(){
        return store.get();
    }


    /**
     * Sets cookies according to uri and responseHeaders
     *
     * @param uri the specified uri
     *            <p/>
     *            a list of request headers
     * @throws IOException if some error of I/O operation occurs
     */

    public void put(URI uri, List<Cookie> cookies) throws IOException {
        if (uri == null || cookies == null) {
            throw new IllegalArgumentException();
        }
        // parse and construct cookies according to the map
        for (Cookie cookie : cookies) {
            store.add(HttpUrl.get(uri), cookie);
        }
    }


    public void put(List<Cookie> cookies){
        if(cookies == null){
            throw new IllegalArgumentException();
        }

        for(Cookie cookie:cookies){
            store.add(cookie);
        }
    }


    /**
     * Sets the cookie policy of this cookie manager.
     * <p/>
     * ACCEPT_ORIGINAL_SERVER is the default policy for OkCookieManager.
     *
     * @param cookiePolicy the cookie policy. if null, the original policy will not be
     *                     changed.
     */
    public void setCookiePolicy(CookiePolicy cookiePolicy) {
        if (cookiePolicy != null) {
            policy = cookiePolicy;
        }
    }

}
