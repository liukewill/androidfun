package com.example.think.androidfun.designpattern.a.model;

/**
 * Created by kenan on 17/5/10.
 */
public abstract class AbsModel {

    private boolean sort;

    protected  abstract void doSomeThing1();

    protected  abstract void doSomeThing2();


    public void justDoIt(){
        if(isAsc()) {
            doSomeThing1();
            doSomeThing2();
        }else{
            doSomeThing2();
            doSomeThing1();
        }
    }

    private boolean isAsc(){
        return sort;
    }

    public void setSort(boolean sort){
        this.sort=sort;
    }

}
