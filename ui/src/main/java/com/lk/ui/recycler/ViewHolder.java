package com.lk.ui.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kenan on 17/5/9.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;


    public ViewHolder(Context context,View itemView) {
        super(itemView);
        mContext=context;
        mConvertView=itemView;
        mViews=new SparseArray<View>();
    }

    public static ViewHolder createViewHolder(Context context,View itemView){
        ViewHolder holder=new ViewHolder(context,itemView);
        return  holder;
    }

    public static ViewHolder createViewHolder(Context context,ViewGroup parent,int layoutId){
        View itemView =LayoutInflater.from(context).inflate(layoutId,parent,false);
        return  createViewHolder(context,itemView);
    }


    /**
     * 如果未找到view 则存入集合
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if(view ==null){
            view =mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }


    /**
     *
     * 辅助方法
     *
     * */

    public void setText(int id,String s){
        TextView tv=getView(id);
        tv.setText(s);
    }

}
