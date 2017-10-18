package com.example.think.androidfun.okhttp.bean;

import java.lang.reflect.ParameterizedType;

/**
 * Created by kenan on 16/8/16.
 */
public abstract class SupperBean<T> {

    public Class<T> getTClass()
    {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }
}
