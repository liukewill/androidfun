package com.example.think.androidfun.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Created by kenan on 16/12/12.
 */
public class StringFormatUtil {


    public static void setHightLightText(Context context, TextView textView, String wholeStr, String highlightStr,
                                         int normalColor, int hightColor) {
        int start ;
        int end ;

        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
            if (wholeStr.contains(highlightStr)) {
                //返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                start = wholeStr.indexOf(highlightStr);
                end = start + highlightStr.length();
            } else {
                return;
            }
        } else {
            return;
        }

        SpannableStringBuilder spBuilder = new SpannableStringBuilder(wholeStr);
        CharacterStyle charaStyle = new ForegroundColorSpan(context.getResources().getColor(hightColor));
        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (textView != null) {
            textView.setTextColor(normalColor);
            textView.setText(spBuilder);
        }
    }

    public static SpannableStringBuilder getHightLightSpannableBuilder(Context context,  String wholeStr, String highlightStr,
                                         int hightColor) {
        int start ;
        int end ;

        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
            if (wholeStr.contains(highlightStr)) {
                //返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                start = wholeStr.indexOf(highlightStr);
                end = start + highlightStr.length();
            } else {
                return null;
            }
        } else {
            return null;
        }

        SpannableStringBuilder spBuilder = new SpannableStringBuilder(wholeStr);
        CharacterStyle charaStyle = new ForegroundColorSpan(context.getResources().getColor(hightColor));
        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spBuilder;
    }


}
