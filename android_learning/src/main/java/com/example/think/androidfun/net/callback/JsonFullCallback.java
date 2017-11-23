package com.example.think.androidfun.net.callback;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Full amount Json parsing, All the data returned from the server parsed into a corresponding Model
 * 
 * Users need to be concerned with three methods: onRequestComplete, onCallCancel, onCallFailure
 * 
 * @author tangfuling
 *
 */

public abstract class JsonFullCallback<T> extends JsonCallback {
	
	public abstract void onRequestComplete(T data);
	
	@SuppressWarnings("unchecked")
	private Class<T> getGenericClass() {
		Type type = getClass().getGenericSuperclass();
		Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) arguments[0];
	}

	/*
	 * Parses the Json data into a corresponding Model
	 */

	@Override
	public void onCallSuccess(Call call, Response response, String responseString) {
		T data = null;
		try {
			data = new Gson().fromJson(responseString, getGenericClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		onRequestComplete(data);
	}
}
