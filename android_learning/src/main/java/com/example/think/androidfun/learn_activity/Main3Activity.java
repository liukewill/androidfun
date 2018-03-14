package com.example.think.androidfun.learn_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.think.androidfun.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Main3Activity extends AppCompatActivity {

    public static final String TAG="LK";
    public static final String BUNDLE_KEY1="BDKEY";
    @Bind(R.id.test_t1)
    TestTextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        Log.i(TAG,"ON-CREATE");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"ON-RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"ON-PAUSE");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_KEY1,"旋转了");
        Log.i(TAG,"ON-SAVE");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG,"ON-RESTORE");
        String s=(String)savedInstanceState.get(BUNDLE_KEY1);
        t1.setText(s);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"ON-STOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"ON-DESTORY");
    }
}
