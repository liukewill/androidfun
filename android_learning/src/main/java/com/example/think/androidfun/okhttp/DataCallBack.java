package com.example.think.androidfun.okhttp;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kenan on 16/8/16.
 */
public abstract class DataCallBack<T> extends MainThreadCallBack {
    public String dataKey = "HeWeather data service 3.0";


    /**
     * 通过继承+反射 获取泛型参数类型
     * 必须继承是因为无法从当前类获取泛型参数,java只提供了getsupperclass 和getGenericSupperclass
     * getActualTypeArguments 可以获取多个参数,具体第几个通过数组取
     *
     * @return
     */
    protected Class<T> getGenericClass() {
        try {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
                return (Class<T>) arguments[0];
            } else {
                return null;
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void setDataKey(String key) {
        this.dataKey = key;
    }

    public abstract void onDataSuccess(List<T> data);

    @Override
    protected void onSuccess(Call call, Response response, String bodyStr) {
        onDataSuccess(jsonToBean(bodyStr));
    }

    @Override
    protected void onFail(Call call, Exception e) {

    }

    @Override
    protected void onCancel(Call call, Exception e) {

    }

    /**
     * 解析成bean 解析失败返回null
     *
     * @param jsonstr
     * @return
     */
    private List<T> jsonToBean(String jsonstr) {
        List<T> data = new ArrayList<T>();
        try {
            JSONObject jsonObject = new JSONObject(jsonstr);
            JSONArray dataArray = jsonObject.getJSONArray(dataKey);
            for(int i=0;i<dataArray.length();i++){
                JSONObject obj=(JSONObject)dataArray.get(i);
                T t=new Gson().fromJson(obj.toString(),getGenericClass());
                data.add(t);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public <T> List<T> listEntity(JsonArray array, Class<T> clazz) {
        try {
            List<T> lst = new ArrayList<T>();
            for (final JsonElement json : array) {
                T entity = new Gson().fromJson(json, clazz);
                lst.add(entity);
            }

            return lst;

        } catch (Exception e) {
            return null;
        }
    }

}
