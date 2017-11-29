package design.createpattern.singleton;

import java.io.Serializable;

/**
 * Created by kenan on 17/9/16.
 * 单例vs静态方法
 *
 * 1.静态方法与非静态方法 内存存储一致，不存在静态方法内存占用问题
 *  1.内存分配上无区别，堆分 垃圾回收堆GC heap 和加载堆 loader heap
 *  GC heap存储实例，受到GC控制，
 *  Loader Heap存储元信息，TYPE对象相关，数据类型，静态字段，实现接口，构造方法，其他方法，静态域
 *  静态方法与非静态方法在类第一次加载后常驻内存，无区别。
 *
 * 2.在内存中速度的区别是，非静态方法在创建实例对象时，因为属性的值对于每个对象都各不相同，因此在new一个实例时，会把这个实例属性在GC Heap里拷贝一份，同时这个new出来的对象放在堆栈上，堆栈指针指向了刚才拷贝的那一份实例的内存地址上。而静态方法则不需要，因为静态方法里面的静态字段，就是保存在Method Table里了，只有一份。

 因此静态方法和非静态方法，在调用速度上，静态方法速度一定会快点，因为非静态方法需要实例化，分配内存，但静态方法不用，但是这种速度上差异可以忽略不计。
 *
 * 3.早期没有面向对象，面向过程编程时期，几乎都是静态方法。
 *
 *
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
