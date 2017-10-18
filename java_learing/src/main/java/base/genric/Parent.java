package base.genric;

/**
 * Created by kenan on 17/5/23.
 */
public abstract  class Parent <T>{

    protected T t;

    protected T getT(){
        return t;
    }

    public abstract void hello(T t);
}
