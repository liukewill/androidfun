package frame.dagger;

/**
 * Created by kenan on 17/4/24.
 * Dependency Injection 依赖注入
 */
public class DaggerDemo {



    public class A{
        B b;
        public A(){
            b=new B();
        }
    }

    public class B{

    }

}
