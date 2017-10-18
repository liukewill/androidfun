package com.example.think.androidfun;

import android.app.Application;
import android.content.Context;

import com.example.think.androidfun.hookactivity.HookUtil;

/**
 * Created by kenan on 17/6/9.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            HookUtil.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
