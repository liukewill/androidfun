package com.example.think.androidfun.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.think.androidfun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenan on 16/9/5.
 */
public class CardPageAdapter extends PagerAdapter  {
    private List<View> mViews;
    private List<String> mData;
    private float mBaseElevation;

    public CardPageAdapter(){
        mData = new ArrayList<>();
        mViews = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            mData.add("");
            mViews.add(null);
        }
    }



    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext())
                .inflate(R.layout.page_adapter_layout,container,false);
//        CardView cardView = (CardView) view.findViewById(R.id.cardview);
//        if (mBaseElevation == 0) {
//            mBaseElevation = cardView.getCardElevation();
//        }
//
//        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }
}
