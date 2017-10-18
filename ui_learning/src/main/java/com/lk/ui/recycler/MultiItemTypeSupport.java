package com.lk.ui.recycler;

/**
 * Created by kenan on 17/5/9.
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
    int getItemViewType(int position,T t);
}
