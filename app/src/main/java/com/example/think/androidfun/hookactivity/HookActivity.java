package com.example.think.androidfun.hookactivity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.think.androidfun.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Hook acitivity
 * Hook掉startActivity这个方法，使得每次调用这个方法之前输出一条日志
 * Hook点 容易找到的对象,静态变量和单利
 */
public class HookActivity extends AppCompatActivity {
    @Bind(R.id.btnnn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        ButterKnife.bind(this);
        ListView listView=null;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {

            private int a;

            @Override
            public void onClick(View v) {

            }

            private void b(){
                a=a*a;
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    /**
     *
     */
    private void getHookActivityThread() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);//静态方法

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void attachContext() throws Exception {
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
