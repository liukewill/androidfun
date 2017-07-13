package com.example.think.androidfun.hookactivity;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by kenan on 17/6/9.
 */
public class HookUtil {

    public static void attachContext() throws Exception {
        //获取ActivityThread 对象 静态变量名称获取 静态变量名在不同sdk下 名称改变了
//        Class<?> activityThreadClass=Class.forName("android.app.ActivityThread");//获取ActivityThread.class
//        Field currentField=activityThreadClass.getField("currentActivityThread");//获取静态变量ActivityThread field
//        currentField.setAccessible(true);
//        Object currentActivityThread=currentField.get(null);//获取静态变量

        //获取ActivityThread 中 mInstrumentation自段
//        Field instrumen=activityThreadClass.getField("mInstrumentation");
//        instrumen.setAccessible(true);
//        Instrumentation baseIns=(Instrumentation)instrumen.get(currentActivityThread);


        //通过静态方法获取 ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);//静态方法

        Field[] fields = activityThreadClass.getDeclaredFields();
        Field mBseField = null;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getType() == Instrumentation.class) {
                mBseField = fields[i];
            }
        }

        mBseField.setAccessible(true);
        Instrumentation mbaseIns = (Instrumentation) mBseField.get(currentActivityThread);

        //代理Instrumentation
        HookInstrumentiationProxy instrumentiationProxy = new HookInstrumentiationProxy(mbaseIns);
        mBseField.set(currentActivityThread, instrumentiationProxy);
    }
}
