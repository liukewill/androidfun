package design.behaviorpattern.model;

/**
 * Created by kenan on 17/5/10.
 * 模版方法模式:基类定义方法模版,子类实现
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

    /**
     * hook method
     * @return
     */
    private boolean isAsc(){
        return sort;
    }

    public void setSort(boolean sort){
        this.sort=sort;
    }

}
