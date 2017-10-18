package design.createpattern.singleton;

import java.io.Serializable;

/**
 * Created by kenan on 17/9/16.
 */

public class Singleton implements Serializable{

    private transient  static  Singleton instance;
    public transient String name;

    protected Singleton(String name){
        this.name=name;
        System.out.println("super-"+name);
    }

    public static Singleton getInstance(){
        return new Singleton("HELLO");
    }
}
