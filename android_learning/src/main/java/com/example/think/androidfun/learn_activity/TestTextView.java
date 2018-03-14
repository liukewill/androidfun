package com.example.think.androidfun.learn_activity;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by kenan on 18/3/13.
 */

public class TestTextView extends TextView {
    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public Parcelable onSaveInstanceState() {
        Log.i(Main3Activity.TAG,"TextView-Onsave");
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Log.i(Main3Activity.TAG,"TextView-OnRestore");
        super.onRestoreInstanceState(state);
    }
}
