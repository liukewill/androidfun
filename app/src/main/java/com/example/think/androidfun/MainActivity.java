package com.example.think.androidfun;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String URL_1 = "https://imgsrc.baidu.com/forum/pic/item/962bd40735fae6cd4b552dfb0fb30f2443a70f42.jpg";
    public static final String URL_2 = "http://pic.58pic.com/58pic/13/82/51/60958PICt46_1024.jpg";
    public static final String URL_3 = "http://d.hiphotos.baidu.com/zhidao/pic/item/7af40ad162d9f2d32420a193aaec8a136327cc9d.jpg";
    public static final String URL_4 = "http://sc.jb51.net/uploads/allimg/140603/11-1406031H254507.jpg";
    public static final String URL_5 = "http://wenwen.soso.com/p/20100108/20100108200727-297769485.jpg";
    public static final String URL_GIF1 = "http://photo.l99.com/bigger/03/1335014372604_7xu278.gif";
    public static final String URL_GIF2 = "http://ww2.sinaimg.cn/mw690/620233dbgw1f40hnk9ermg20a00a01kz.gif";

    @Bind(R.id.iv_1)
    ImageView iv1;
    @Bind(R.id.iv_2)
    ImageView iv2;
    @Bind(R.id.iv_3)
    ImageView iv3;
    @Bind(R.id.iv_4)
    ImageView iv4;
    @Bind(R.id.iv_5)
    ImageView iv5;
    @Bind(R.id.tv_1)
    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scrolling);
        ButterKnife.bind(this);
        Glide.get(this).clearMemory();
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        Glide.get(this).clearMemory();
        Glide.get(this).clearDiskCache();
    }

    private void initImage() throws ExecutionException, InterruptedException {
        SimpleTarget simpleTarget=new SimpleTarget() {
            @Override
            public void onResourceReady(Object resource, GlideAnimation glideAnimation) {

            }
        };
        //加载网络图片
        Glide.with(MainActivity.this)
                .load(URL_1)
                .into(iv1);

        Glide.with(this)
                .load(URL_GIF2)
                .asGif()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .thumbnail(0.1f)
                .into(iv1);
        Glide.with(this)
                .load(R.drawable.load)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.mipmap.ic_launcher)
                .into(iv2);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
