package com.example.think.androidfun.touchevent;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by kenan on 18/3/27.
 */

public class RecyclerPlus extends RecyclerView {
    public RecyclerPlus(Context context) {
        super(context);
    }

    public RecyclerPlus(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerPlus(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Rect rect=new Rect();
//        getLocalVisibleRect(rect);
//        Log.i("LKK","rect"+rect.height());
//        if(ev.getAction()==MotionEvent.ACTION_MOVE){
//            if(rect.height()==getHeight()){
//                this.getParent().requestDisallowInterceptTouchEvent(true);
//            }
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
//        switch (e.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.i("LKK","R-intercept-DOWN---");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("LKK","R-intercept-MOVE--");
//                break;
//        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        switch (e.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.i("LKK","R-ontouch-DOWN---");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("LKK","R-ontouch-MOVE---");
//                break;
//        }
        return super.onTouchEvent(e);
    }
}
