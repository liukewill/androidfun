package com.lk.ui.recycler;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kenan on 17/5/9.
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    protected  MultiItemTypeSupport<T> multiItemTypeSupport;

    public MultiItemCommonAdapter(Context mContext,List<T> mDatas,MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(mContext, -1, mDatas);
        this.multiItemTypeSupport=multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return multiItemTypeSupport.getItemViewType(position,mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId=multiItemTypeSupport.getLayoutId(viewType);
        ViewHolder holder=ViewHolder.createViewHolder(mContext,parent,layoutId);
        return holder;
    }
}
