package com.example.think.androidfun.butterkinfe;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by kenan on 17/7/10.
 */

public class DynamicHandelr implements InvocationHandler{

    private WeakReference<Object> handlerRef;
    private final HashMap<String,Method> methodHashMap=new HashMap<>();


    public DynamicHandelr(Object handelr){
        this.handlerRef=new WeakReference<Object>(handelr);
    }

    public void addMethod(String name,Method method){
        methodHashMap.put(name,method);
    }

    public Object getHandler(){
        return handlerRef.get();
    }

    public void setHandlerRef(Object handler){
        this.handlerRef=new WeakReference<Object>(handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object handler=handlerRef.get();
        if(handler!=null){
            String methodName=method.getName();
            method=methodHashMap.get(methodName);
            if(method!=null){
                method.setAccessible(true);
                return method.invoke(handler,args);
            }
        }
        return null;
    }
}
