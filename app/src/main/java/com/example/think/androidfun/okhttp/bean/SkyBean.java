package com.example.think.androidfun.okhttp.bean;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by kenan on 16/8/16.
 */
public abstract class SkyBean<K,V> {

    public SkyBean(){

    }
    @SuppressWarnings("unchecked")
    public Class<K> getKtype(){
        Type type=this.getClass().getGenericSuperclass();//获取泛型父类 不知道为啥是父类
        Log.i("kenan", "GenericSuperclass:" +type);
        ParameterizedType parameterizedType=(ParameterizedType)type;//参数化类型
        Type[]types=parameterizedType.getActualTypeArguments();//类型列表
        Log.i("kenan", "ParameterizedType:" +types.length);
        Class<K> instance=(Class<K>) types[0];//只有一个类型时 取第一个,多个则按照顺序取
        Log.i("kenan", "T--->:" +instance);
        return instance;
    }

    @SuppressWarnings("unchecked")
    public Class<V> getVtype(){
        Type type=this.getClass().getGenericSuperclass();//获取泛型父类 不知道为啥是父类
        Log.i("kenan", "GenericSuperclass:" +type);
        ParameterizedType parameterizedType=(ParameterizedType)type;//参数化类型
        Type[]types=parameterizedType.getActualTypeArguments();//类型列表
        Log.i("kenan", "ParameterizedType:" +types.length);
        Class<V> instance=(Class<V>) types[1];//只有一个类型时 取第一个,多个则按照顺序取
        Log.i("kenan", "T--->:" +instance);
        return instance;
    }


//    @SuppressWarnings("unchecked")
//    public Class<T> getClassType(){
//        Type type = getClass().getGenericSuperclass();
//        Log.i("kenan", "GenericSuperclass:" +type);
//        Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
//        return (Class<T>) arguments[0];
//    }

    public abstract void dosth();
}
