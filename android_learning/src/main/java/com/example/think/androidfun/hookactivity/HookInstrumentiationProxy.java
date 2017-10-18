package com.example.think.androidfun.hookactivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by kenan on 17/6/8.
 */
public class HookInstrumentiationProxy extends Instrumentation {

    private Instrumentation baseInstrumen;

    public HookInstrumentiationProxy(Instrumentation baseInstrumen) {
        this.baseInstrumen = baseInstrumen;
    }

    /**
     * hook method
     */
    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target,
                                            Intent intent, int requestCode, Bundle options){

        Log.i("LKK","hook-start");

        try{
            Method execStartActivity=Instrumentation.class.getDeclaredMethod("execStartActivity",Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult)execStartActivity.invoke(baseInstrumen,who,contextThread,token,target,intent,requestCode,options);
        }catch (Exception e){
            return null;
        }
    }
}
