package com.example.think.androidfun;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.think.androidfun.view.scrollconflict.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GlideTestActivity extends AppCompatActivity {
    public static final String URL_2 = "http://pic.58pic.com/58pic/13/82/51/60958PICt46_1024.jpg";


    @Bind(R.id.glide_iv)
    ImageView glideIv;
    @Bind(R.id.btn)
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);
        ButterKnife.bind(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.with(getApplicationContext())
                            .load(URL_2)
                            .into(new GlideDrawableImageViewTarget(glideIv){

                            });
                }catch (Exception e){
                    Log.i("FUN","GLIDE GUA LE");
                }
             /*   if(isFinishing()){
                    return;
                }*/
                GlideUtils.glideLoader(GlideTestActivity.this,URL_2,R.mipmap.ic_launcher,R.mipmap.ic_launcher,glideIv);
            }
        }, 1000 * 5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideTestActivity.this.finish();
            }
        });
    }
}
