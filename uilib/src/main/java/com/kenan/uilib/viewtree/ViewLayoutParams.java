package com.kenan.uilib.viewtree;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by kenan on 17/12/5.
 * 初始化view的方式
 * 1.new XXView()  无LayoutParameters
 * 2.inflate(R.layout.xx,parent,false)  有parent 就有LayoutParameters
 * 3.inflate(R.layout.xx,null)
 */

public class ViewLayoutParams extends LinearLayout{



    public ViewLayoutParams(Context context) {
        super(context);
    }

    public ViewLayoutParams(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewLayoutParams(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
