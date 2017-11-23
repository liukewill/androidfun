package com.example.think.androidfun.net.callback;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by iwm on 16/6/8.
 */
public abstract class FileCallback extends WMCallback {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public abstract void onCallCancel(Call call);

    public abstract void onCallSuccess(Call call, Response response);

    public abstract void onCallFailure(Call call, IOException e);

    public abstract void onCallProgress(long bytesWritten, long totalSize);

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (call.isCanceled()) {
                    onCallCancel(call);
                } else {
                    onCallFailure(call, e);
                }
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        saveFile(response);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response.code() != HttpURLConnection.HTTP_OK) {
                    onCallFailure(call, new IOException("Unexpected code from onResponse" + response));
                } else {
                    onCallSuccess(call, response);
                }
            }
        });
    }

    private File saveFile(Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onCallProgress(finalSum, total);
                    }
                });
            }
            fos.flush();

            return file;

        } finally {
            try {
                response.body().close();
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {

            }
        }
    }
}
