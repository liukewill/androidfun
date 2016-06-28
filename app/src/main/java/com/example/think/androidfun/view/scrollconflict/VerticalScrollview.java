package com.example.think.androidfun.view.scrollconflict;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by think on 2016/6/16.
 */
public class VerticalScrollview extends ScrollView {

    private float xdistance,ydistance;

    private float xLast,yLast;

    private int move =0;

    private int up =0;

    private int down=0;


    public VerticalScrollview(Context context) {
        super(context);
    }

    public VerticalScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("FUN","第  "+ down +" 次触发--------DOWN--------");
                move =0;
                xdistance=ydistance=0;//清零
                xLast=ev.getX();
                xLast=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("FUN","第  "+ move +" 次触发MOVE");
                move++;
                final float curX=ev.getX();
                final float curY=ev.getY();

                xdistance +=Math.abs(curX-xLast);
                ydistance +=Math.abs(curY-yLast);
                xLast=curX;
                yLast=curY;
                if(xdistance>ydistance)//X轴距离滑动大于Y轴距离，scrollview 不拦截事件
                {
                    Log.i("FUN","viewpager拦截");
                    return false;
                }else{
                    Log.i("FUN","scrollview拦截");
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("FUN","第  "+ up +" 次触发--------UP--------");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
