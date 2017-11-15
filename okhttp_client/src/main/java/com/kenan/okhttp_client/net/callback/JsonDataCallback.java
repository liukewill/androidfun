package com.kenan.okhttp_client.net.callback;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Part of the Json parsing, The server returned data parsed into three parts errno, errmsg, data
 * <p/>
 * Users need to be concerned with three methods: onRequestComplete, onCallCancel, onCallFailure
 *
 * @author tangfuling
 */

public abstract class JsonDataCallback<T> extends JsonCallback {

    // key
    private String errnoKey = "";
    private String errmsgKey = "";
    private String dataKey = "";

    public abstract void onRequestComplete(int errno, String errmsg, T data);

    public JsonDataCallback() {
        this.errnoKey = getErrnoKey();
        this.errmsgKey = getErrmsgKey();
        this.dataKey = getDataKey();
    }

    public String getErrnoKey() {
        return "errno";
    }

    public String getErrmsgKey() {
        return "errmsg";
    }

    public String getDataKey() {
        return "data";
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getGenericClass() {
        Type type = getClass().getGenericSuperclass();
        Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<T>) arguments[0];
    }

	/*
     * Parses the Json data into three parts errno, errmsg, data
	 */

    @Override
    public void onCallSuccess(Call call, Response response, String responseString) {
        int errno = -1;
        String errmsg = "";
        T data = null;

        try {
            JSONObject jsonObject = new JSONObject(responseString);
            errno = jsonObject.getInt(errnoKey);
            errmsg = jsonObject.getString(errmsgKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // parse data
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            JSONObject dataObject = jsonObject.getJSONObject(dataKey);
            if (dataObject != null) {
                data = new Gson().fromJson(dataObject.toString(), getGenericClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        onRequestComplete(errno, errmsg, data);
    }
}
