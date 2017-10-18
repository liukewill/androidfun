package com.example.think.androidfun.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by kenan on 16/7/22.
 * 通过handler post 将callback在ui线程中执行
 */
public abstract class MainThreadCallBack implements Callback {

    private Handler handler=new Handler(Looper.getMainLooper());

    /**
     * ui线程中执行该方法,不可执行耗时操作.参数response不可以执行io读取操作
     * @param call
     * @param response
     * @param bodyStr
     */
    protected abstract void onSuccess(Call call,Response response,String bodyStr);

    protected abstract void onFail(Call call,Exception e);

    protected abstract void onCancel(Call call,Exception e);

    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(call.isCanceled()){
                    onCancel(call,new IOException("Net call canceled"));
                }else{
                    onFail(call,e);
                }

            }
        });

    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        final String responseStr=response.body().string();//不能放到ui线程执行
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(response.code()==200){
                    onSuccess(call,response,responseStr);
                }else{
                    onFail(call,new IOException("Unexpected code from onResponse"+response.toString()));
                }
            }
        });
    }
}
