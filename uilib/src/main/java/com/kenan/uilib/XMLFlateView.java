package com.kenan.uilib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kenan on 17/12/19.
 * 使用xml布局必须使用  super函数
 * 避免在xml布局中引起错误
 * 继承自view 或者viewgroup时候一定要注意
 * viewinflate 方法错误会导致  android.view.InflateException:
 *
 * XML 命名错误，会导致xml parser block 错误
 */

public class XMLFlateView  extends View {


    public XMLFlateView(Context context) {
        super(context);
    }

    public XMLFlateView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public XMLFlateView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
