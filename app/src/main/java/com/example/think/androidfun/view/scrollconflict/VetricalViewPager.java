package com.example.think.androidfun.view.scrollconflict;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by kenan on 16/6/30.
 */
public class VetricalViewPager extends ViewPager {
    public VetricalViewPager(Context context) {
        super(context);
    }

    public VetricalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch ( ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.i("FUN","VP--dispatch---ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("FUN","VP--dispatch---ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.i("FUN","VP--dispatch---ACTION_UP");
//                break;
//        }
//
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch ( ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("FUN","VP--IT---ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("FUN","VP--IT---ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("FUN","VP--IT---ACTION_UP");
                break;
        }
        Log.i("FUN","VP---IT---"+super.onInterceptTouchEvent(ev));

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
                switch ( ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("FUN","VP--TE---ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("FUN","VP--TE---ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("FUN","VP--TE---ACTION_UP");
                break;
        }
//        Log.i("FUN","VP---ONTOUCHEVENT---"+super.onTouchEvent(ev));
        Log.i("FUN","VP---ONTOUCHEVENT---"+super.onTouchEvent(ev));

        return false;
    }
}
