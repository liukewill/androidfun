package base.obj_clazz;

import java.lang.reflect.Type;

/**
 * Created by kenan on 17/6/19.
 *
 * java class 编译完成后,生成Class对象,对应.class文件
 * 获取Class对象
 * 1)对象.getClass()
 * 2)Class.forName();
 * 3).class
 *
 * Object
 * equals 默认比较内存地址 可复写
 * hashcode 内存地址转成int
 * tostring this.getclass.gename+@+Integer.toHexString(hashcode());
 * clone
 *
 * 先有Object 后有Class
 * Class描述类 meta元信息,jvm根据.class字节码加载对象
 *
 *
 */
public class Hello {
    static Class aClass=Integer.TYPE;
    static Hello hello=new Hello();


    public static void main(String[]s){
        System.out.println(hello.toString());
        System.out.println(hello.hashCode());
        System.out.println(Integer.toHexString(hello.hashCode()));


    }

}

