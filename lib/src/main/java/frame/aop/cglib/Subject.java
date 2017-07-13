package frame.aop.cglib;

/**
 * Created by kenan on 17/6/11.
 * cglib -nodep-2.2 +asm使用
 * cglib使用字节码动态生成子类
 * 不可对final类生成代理类
 * 不可对final方法代理,可执行final方法
 */
public  class Subject {
    public void hello(){
        System.out.println("hello");
    }

    public final void world(){
        System.out.println("world");
    }
}
