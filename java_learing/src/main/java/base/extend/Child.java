package base.extend;

/**
 * Created by kenan on 17/12/6.
 */

public class Child extends Parent {

    public void  b(){

    }

    public void c(){
        Parent c=new Child();
        Child d=(Child)c;
        d.b();
    }
}
