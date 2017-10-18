package com.lk.ui.loadview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.lk.ui.Util;

/**
 * Created by kenan on 17/5/11.
 */
public class LoadStateView extends FrameLayout {

    private View loadingView;
    private View emptyView;
    private View retryView;
    private View contentView;
    private LayoutInflater inflater;


    public LoadStateView(Context context) {
        this(context,null);
    }

    public LoadStateView(Context context, AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public LoadStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater=LayoutInflater.from(context);
    }

    public void setLoadingView(int layoutId){
        setLoadingView(inflater.inflate(layoutId,this,false));
    }

    public void setEmptyView(int layoutId){
        setEmptyView(inflater.inflate(layoutId,this,false));
    }

    public void setRetryView(int layoutId){
        setRetryView(inflater.inflate(layoutId,this,false));
    }

    public void setContentView(int layoutId){
        setContentView(inflater.inflate(layoutId,this,false));
    }


    public void showView(View view){
       if(view==loadingView){
            Util.showView(loadingView);
            Util.hideView(emptyView);
            Util.hideView(retryView);
            Util.hideView(contentView);
       }else if(view==emptyView){
           Util.showView(emptyView);
           Util.hideView(loadingView);
           Util.hideView(retryView);
           Util.hideView(contentView);
       }else if(view==retryView){
           Util.showView(retryView);
           Util.hideView(loadingView);
           Util.hideView(emptyView);
           Util.hideView(contentView);
       }else if(view==contentView){
           Util.showView(retryView);
           Util.hideView(loadingView);
           Util.hideView(emptyView);
           Util.hideView(contentView);
       }
    }


    public void setLoadingView(View loadingView) {
        removeView(this.loadingView);
        this.loadingView = loadingView;
        addView(this.loadingView);
    }

    public void setEmptyView(View emptyView) {
        removeView(this.emptyView);
        this.emptyView = emptyView;
        addView(this.emptyView);
    }


    public void setRetryView(View retryView) {
        removeView(this.retryView);
        this.retryView = retryView;
        addView(this.retryView);
    }

    public void setContentView(View contentView) {
        removeView(this.contentView);
        this.contentView = contentView;
        addView(this.contentView);
    }


    public View getLoadingView() {
        return loadingView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    public View getRetryView() {
        return retryView;
    }

    public View getContentView() {
        return contentView;
    }
}
