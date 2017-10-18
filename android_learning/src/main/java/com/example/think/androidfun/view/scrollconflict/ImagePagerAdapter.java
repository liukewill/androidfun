package com.example.think.androidfun.view.scrollconflict;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.think.androidfun.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/6/16.
 */
public class ImagePagerAdapter extends PagerAdapter {
    Context context;

    List<String> dataList=new ArrayList<>();
    public ImagePagerAdapter(Context context){
        this.context=context;
    }

    public void setDdata(List<String> list){
        this.dataList=list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return dataList==null?0:dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(context)
                .load(dataList.get(position))
                .error(R.mipmap.ic_launcher)
                .into(imageView);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
