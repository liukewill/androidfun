package com.example.think.androidfun.touchevent;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by kenan on 18/3/27.
 */


    public  class ScrollPlus extends ScrollView {
        View innerView;
    Rect rect=new Rect();


    public ScrollPlus(Context context) {
            super(context);
        }

        public ScrollPlus(Context context, AttributeSet attrs) {
            super(context, attrs);

        }

        public ScrollPlus(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        innerView.getLocalVisibleRect(rect);
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            if(rect.height()!=innerView.getHeight()){
                super.dispatchTouchEvent(ev);
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        return super.onTouchEvent(ev);
    }

    @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if(rect.height()!=innerView.getHeight()){
                super.onInterceptTouchEvent(ev);
                return true;
            }else{
                return super.onInterceptTouchEvent(ev);
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

        public void setInnerView(View view){
            this.innerView=view;
        }
}
