package com.example.think.androidfun.net.cookie;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;


/**
 * Created by Ghost on 16/3/10.
 */

public class SerializableHttpCookie implements Serializable {

    private transient final Cookie cookies;
    private transient Cookie clientCookies;

    public SerializableHttpCookie(Cookie cookies) {
        this.cookies = cookies;
    }

    public Cookie getCookies() {
        Cookie bestCookies = cookies;
        if (clientCookies != null) {
            bestCookies = clientCookies;
        }
        return bestCookies;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(cookies.name());
        out.writeObject(cookies.value());
        out.writeLong(cookies.expiresAt());
        out.writeObject(cookies.domain());
        out.writeObject(cookies.path());
        out.writeBoolean(cookies.secure());
        out.writeBoolean(cookies.httpOnly());
        out.writeBoolean(cookies.hostOnly());
        out.writeBoolean(cookies.persistent());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        long expiresAt = in.readLong();
        String domain = (String) in.readObject();
        String path = (String) in.readObject();
        boolean secure = in.readBoolean();
        boolean httpOnly = in.readBoolean();
        boolean hostOnly = in.readBoolean();
        boolean persistent = in.readBoolean();
        Cookie.Builder builder = new Cookie.Builder();
        builder = builder.name(name);
        builder = builder.value(value);
        builder = builder.expiresAt(expiresAt);
        builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
        builder = builder.path(path);
        builder = secure ? builder.secure() : builder;
        builder = httpOnly ? builder.httpOnly() : builder;
        clientCookies = builder.build();
    }
}//
///**
// * from http://stackoverflow.com/questions/25461792/persistent-cookie-store-using-okhttp-2-on-android
// * and<br/>
// * http://www.geebr.com/post/okHttp3%E4%B9%8BCookies%E7%AE%A1%E7%90%86%E5%8F%8A%E6%8C%81%E4%B9%85%E5%8C%96
// */
//
//public class SerializableHttpCookie implements Serializable
//{
//    private static final long serialVersionUID = 6374381323722046732L;
//
//    private transient final Cookie cookie;
//    private transient Cookie clientCookie;
//
//    public SerializableHttpCookie(Cookie cookie)
//    {
//        this.cookie = cookie;
//    }
//
//    public Cookie getCookie()
//    {
//        Cookie bestCookie = cookie;
//        if (clientCookie != null)
//        {
//            bestCookie = clientCookie;
//        }
//
//        return bestCookie;
//    }
//
//    private void writeObject(ObjectOutputStream out) throws IOException
//    {
//        out.writeObject(cookie.name());
//        out.writeObject(cookie.value());
//        out.writeLong(cookie.expiresAt());
//        out.writeObject(cookie.domain());
//        out.writeObject(cookie.path());
//        out.writeBoolean(cookie.secure());
//        out.writeBoolean(cookie.httpOnly());
//        out.writeBoolean(cookie.hostOnly());
//        out.writeBoolean(cookie.persistent());
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
//    {
//        String name = (String) in.readObject();
//        String value = (String) in.readObject();
//        long expiresAt = in.readLong();
//        String domain = (String) in.readObject();
//        String path = (String) in.readObject();
//        boolean secure = in.readBoolean();
//        boolean httpOnly = in.readBoolean();
//        boolean hostOnly = in.readBoolean();
//        boolean persistent = in.readBoolean();
//        Cookie.Builder builder = new Cookie.Builder();
//        builder = builder.name(name);
//        builder = builder.value(value);
//        builder = builder.expiresAt(expiresAt);
//        builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
//        builder = builder.path(path);
//        builder = secure ? builder.secure() : builder;
//        builder = httpOnly ? builder.httpOnly() : builder;
//        clientCookie = builder.build();
//
//    }
//}