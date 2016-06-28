package com.example.think.androidfun.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.think.androidfun.GlideTestActivity;
import com.example.think.androidfun.R;
import com.example.think.androidfun.view.scrollconflict.ImagePagerAdapter;
import com.example.think.androidfun.view.scrollconflict.VerticalScrollview;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String URL_1 = "https://imgsrc.baidu.com/forum/pic/item/962bd40735fae6cd4b552dfb0fb30f2443a70f42.jpg";
    public static final String URL_2 = "http://pic.58pic.com/58pic/13/82/51/60958PICt46_1024.jpg";
    public static final String URL_3 = "http://d.hiphotos.baidu.com/zhidao/pic/item/7af40ad162d9f2d32420a193aaec8a136327cc9d.jpg";
    public static final String URL_4 = "http://sc.jb51.net/uploads/allimg/140603/11-1406031H254507.jpg";
    public static final String URL_5 = "http://wenwen.soso.com/p/20100108/20100108200727-297769485.jpg";


    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.vs)
    VerticalScrollview vs;

    List<String> dataList = new ArrayList<>();

    ImagePagerAdapter imagePagerAdapter;
    @Bind(R.id.ttt)
    Button ttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
        ttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, GlideTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewPager() {
        dataList.add(URL_1);
        dataList.add(URL_2);
        dataList.add(URL_3);
        dataList.add(URL_4);
        dataList.add(URL_5);

        if (imagePagerAdapter == null) {
            imagePagerAdapter = new ImagePagerAdapter(this);
        }
        viewPager.setAdapter(imagePagerAdapter);
        imagePagerAdapter.setDdata(dataList);
        vs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("FUN", "----ONTOUCH----DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("FUN", "----ONTOUCH----MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("FUN", "----ONTOUCH----UP");
                        break;

                }
                return false;
            }
        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("FUN", "----VIEW--PAGER----DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("FUN", "----VIEW--PAGER----MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("FUN", "----VIEW--PAGER----UP");
                        break;

                }
                return false;
            }
        });
    }


}
