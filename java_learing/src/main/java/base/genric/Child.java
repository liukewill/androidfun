package base.genric;

/**
 * Created by kenan on 17/5/23.
 */
public abstract class Child<T> extends Parent<T>{

    protected T getTT(){
        return t;
    }
}
