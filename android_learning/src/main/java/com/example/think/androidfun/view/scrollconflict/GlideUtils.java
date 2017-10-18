package com.example.think.androidfun.view.scrollconflict;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by liuke on 2016/6/21.
 * glide 加载图片当activity finish之后会报异常，需要try catch。
 * IllegalArgumentExeception ：You cannot start a load for a destroyed activity
 * 多种解决方法 1：activity destroy中cancel所有请求
 *          2.请求callback 中判断isFinishing()==true，直接return。
 *          3.glide context传入applicationcontext
 *          4.try catch （如这个class）
 */
public class GlideUtils {

    /**
     * @param context glide context ，若在非UI线程中，必须传入ApplicationContext
     * @param targetUrl 加载图片Url
     * @param imageView 加载ImageView
     */
    public static void glideLoader(Context context, String targetUrl, ImageView imageView){
        try {
            Glide.with(context)
                    .load(targetUrl)
                    .into(imageView);
        }catch (Exception e){
            Log.e("glide",e.getMessage());
        }
    }

    /**
     * @param context glide context ，若在非UI线程中，必须传入ApplicationContext
     * @param targetUrl  加载图片Url
     * @param placeHolderDrawable  占位图片
     * @param errorDrawable  加载失败图片
     * @param imageView 加载ImageView
     */
    public static void glideLoader(Context context, String targetUrl,int placeHolderDrawable,int errorDrawable, ImageView imageView){
        try {
            Glide.with(context)
                    .load(targetUrl)
                    .placeholder(placeHolderDrawable)
                    .error(errorDrawable)
                    .into(imageView);
        }catch (Exception e){
            Log.e("glide",e.getMessage());
        }
    }
}
