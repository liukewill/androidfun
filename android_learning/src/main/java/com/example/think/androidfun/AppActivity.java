package com.example.think.androidfun;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppActivity extends AppCompatActivity {
    @Bind(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        ButterKnife.bind(this);

    }

    private void sendBoradCastAlive(){
        Intent intent=new Intent();
        intent.setAction("com.aesean.lib.WAKE_BROADCAST");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        this.sendBroadcast(intent);
        Log.i("WAKE","SEND");
    }


    private void sendBroadCastNotAlive(){
        Intent intent=new Intent();
        Context c=null;
        try {
            c=createPackageContext("com.baidu.lbs.commercialism"
            ,Context.CONTEXT_INCLUDE_CODE|CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        intent.setClassName(c,"com.baidu.lbs.WakeUpBroadcast");
        intent.setAction("com.aesean.lib.WAKE_BROADCAST");
//        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        this.sendBroadcast(intent);
        Log.i("WAKE","SEND");

    }





}
