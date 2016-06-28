package com.example.think.androidfun.designpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2016/6/14.
 * 观察者模式
 */
public class ObseverManager {

    private List<OnMessageChangeListner> list=new ArrayList<>();

    public interface OnMessageChangeListner{
        public void onMessageChange(String s);
    }

    public void addListener(OnMessageChangeListner onMessageChangeListner){
        if(onMessageChangeListner!=null&&!list.contains(onMessageChangeListner)){
            list.add(onMessageChangeListner);
        }
    }

    public void removerListner(OnMessageChangeListner onMessageChangeListner){
        if(onMessageChangeListner!=null&&list.contains(onMessageChangeListner)){
            list.remove(onMessageChangeListner);
        }
    }

    public void dataChange(String s){
        for(OnMessageChangeListner listner:list){
            listner.onMessageChange(s);
        }
    }

}
