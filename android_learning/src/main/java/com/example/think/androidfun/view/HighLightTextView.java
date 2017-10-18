package com.example.think.androidfun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kenan on 16/12/12.
 */
public class HighLightTextView extends TextView {
    protected  Context context;
    public HighLightTextView(Context context) {
        super(context);
        this.context=context;
    }

    public HighLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

    }

    public HighLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }


}
