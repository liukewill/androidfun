package com.example.think.androidfun.butterkinfe;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by kenan on 17/7/10.
 */

public class InjectManager {

    public static void injectEvents(final Activity activity){
        Method[] methods=activity.getClass().getDeclaredMethods();
        for (final Method method : methods) {
            if(method.isAnnotationPresent(OnClick.class)){
                OnClick onclickAnnotation=method.getAnnotation(OnClick.class);
                EventBase enventAnnotation=onclickAnnotation.annotationType().getAnnotation(EventBase.class);
                if(enventAnnotation!=null){
                    Class listenerType=enventAnnotation.listenerType();
                    String listenersetter=enventAnnotation.listenerSetter();
                    String methodName=enventAnnotation.methodName();

                    try{
                       int[] id= onclickAnnotation.value();
                        DynamicHandelr dh=new DynamicHandelr(activity);//动态代理
                        dh.addMethod(methodName,method);//添加动态代理方法
                        Object listener= Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class[]{listenerType},dh);

                        for (int i : id) {
                            View view=activity.findViewById(i);
                            Method setMethod=view.getClass().getMethod(listenersetter,listenerType);
                            View.OnClickListener l =new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    try {
                                        method.setAccessible(true);
                                        method.invoke(activity,v);
                                    }catch (Exception e){

                                    }
                                }
                            };
                            setMethod.invoke(view,l);

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
