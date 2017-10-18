package design.createpattern.singleton;

/**
 * Created by kenan on 17/9/16.
 * 父类private 构造函数 子类无法调用super();  所以只有private构造函数的类无法继承
 * 单例可提供protected类型的构造函数供子类调用，场景特殊，特殊使用
 *
 */

public class SubSingle extends Singleton {


    public SubSingle(String name) {
        super(name);
        System.out.println(name);
    }
}
