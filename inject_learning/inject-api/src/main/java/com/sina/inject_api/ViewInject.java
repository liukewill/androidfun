package com.sina.inject_api;

import android.app.Activity;
import android.util.ArrayMap;
import android.util.Printer;

/**
 * Created by kenan on 17/7/13.
 */

public class ViewInject {

    private static  ActivityProvider activityProvider=new ActivityProvider();

    public static ArrayMap<String,Inject> injectArrayMap=new ArrayMap<>();//注解缓存

    public static void inject(Activity activity){
        inject(activity,activity,activityProvider);
    }


    public static void inject(Object host,Object object,Provider provider){
        String className=host.getClass().getName();//注解目标类
        try{
            Inject inject=injectArrayMap.get(className);//从缓存中取，进一步降低反射影响
            if(inject==null){
                Class a=Class.forName(className+"$ViewInject");//反射获取 编译时生成的代码
                inject=(Inject)a.newInstance();
                injectArrayMap.put(className,inject);
            }

            inject.inject(host,object,provider);//调用编译时生成的代码
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
