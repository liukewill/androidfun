package com.example.think.androidfun.okhttp;


import com.example.think.androidfun.okhttp.bean.WeatherInfo;
import com.facebook.stetho.okhttp3.StethoInterceptor;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by kenan on 16/7/21.
 */
public class OKhttpHelper {

    public static final String WEATHER_URL = "http://apis.baidu.com/heweather/weather/free?city=beijing";

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .build();
    public static NetWorkCallBackListener netWorkCallBackListener;

    public static void sendRequest() {


        final Request request = new Request.Builder()
                .url(WEATHER_URL)
                .header("apikey", "cf55fe244ee57aab8864fd7dde598289")
                .build();


//        okHttpClient.newCall(request).enqueue(new MainThreadCallBack() {
//            @Override
//            protected void onSuccess(Call call, Response response,String bodyStr) {
//                if (netWorkCallBackListener != null)
//                    netWorkCallBackListener.onSuccess(call, bodyStr);
//            }
//
//            @Override
//            protected void onFail(Call call, Exception e) {
//                if (netWorkCallBackListener != null)
//                    netWorkCallBackListener.onFailure(call);
//            }
//
//            @Override
//            protected void onCancel(Call call, Exception e) {
//
//            }
//        });

        okHttpClient.newCall(request).enqueue(weatherCallBack);


    }

    public static void setNetWorkCallBackListener(NetWorkCallBackListener netWorkCall) {
        netWorkCallBackListener = netWorkCall;
    }


    public static void removeNetWorkCallBackListener() {
        netWorkCallBackListener = null;
    }



    public interface NetWorkCallBackListener {
        void onFailure(Call call);

        void onSuccess(Object object);
    }


    private static DataCallBack<WeatherInfo> weatherCallBack=new DataCallBack<WeatherInfo>() {
        @Override
        public void onDataSuccess(List<WeatherInfo> data) {
            if (netWorkCallBackListener != null)
                    netWorkCallBackListener.onSuccess(data);
        }
    };

}
