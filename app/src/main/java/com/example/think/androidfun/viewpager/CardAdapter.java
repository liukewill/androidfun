package com.example.think.androidfun.viewpager;

import android.support.v7.widget.CardView;

/**
 * Created by kenan on 16/9/5.
 */
public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
