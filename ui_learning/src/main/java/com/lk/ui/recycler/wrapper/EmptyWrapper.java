package com.lk.ui.recycler.wrapper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.ui.R;
import com.lk.ui.recycler.ViewHolder;


/**
 * Created by kenan on 17/5/10.
 */
public class EmptyWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_EMPTY =10000;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private int mEmptyLayoutId;


    public EmptyWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }

    private boolean isEmpty(){
        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(isEmpty()){
           View ep= LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view,parent,false);
//            if(mEmptyView!=null){
//                viewHolder=ViewHolder.createViewHolder(parent.getContext(),mEmptyView);
//            }else{
//                viewHolder=ViewHolder.createViewHolder(parent.getContext(),parent,mEmptyLayoutId);
//            }
            ep.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.MATCH_PARENT));
           return new ViewHolder(parent.getContext(),ep);
        }else{
           return mInnerAdapter.onCreateViewHolder(parent,viewType);
       }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==ITEM_TYPE_EMPTY) return;
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if(isEmpty()) return 1;
        return mInnerAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(isEmpty()){
            return ITEM_TYPE_EMPTY;
        }else{
            return mInnerAdapter.getItemViewType(position);
        }
    }

    public void setEmptyView(View emptyView)
    {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId)
    {
        mEmptyLayoutId = layoutId;
    }
}
