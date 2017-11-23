package com.example.think.androidfun.net;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by iwm on 16/6/14.
 */
public class RequestParams {

    private HashMap<String, String> mUrlParams = new HashMap<String, String>();

    private HashMap<String, File> mFileParams = new HashMap<String, File>();

    public void put(String key, String value) {
        if (value != null) {
            mUrlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, File value) {
        mFileParams.put(key, value);
    }

    public HashMap<String, String> getUrlParams() {
        return mUrlParams;
    }

    public HashMap<String, File> getFileParams() {
        return mFileParams;
    }

    public RequestBody buildRequestBody() {
        if (mFileParams.size() == 0) {
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            return builder.build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            addParams(builder);
            for (String key : mFileParams.keySet()) {
                File file = mFileParams.get(key);
                if (file != null) {
                    String filename = file.getName();
                    RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(filename)), file);
                    builder.addFormDataPart(key, filename, fileBody);
                }
            }
            return builder.build();
        }
    }




    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void addParams(MultipartBody.Builder builder) {
        for (String key : mUrlParams.keySet()) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                    RequestBody.create(null, mUrlParams.get(key)));
        }
    }

    private void addParams(FormBody.Builder builder) {
        for (String key : mUrlParams.keySet()) {
            builder.add(key, mUrlParams.get(key));
        }
    }
}
