package design.createpattern.prototype;


import java.io.Serializable;

/**
 * Created by kenan on 17/6/16.
 * 原型模式Prototype
 *
 * 方式：
 * impelements clonable 并且覆写clone方法
 *
 * 优点:
 * 1.在内存中进行二进制流的拷贝,比直接使用关键字new性能好很多,
 * 在循环体内产生大量对象时候,优点明显
 * 2.原型模式不执行构造函数,跳过构造函数约束
 *
 * 使用场景:
 * 1.类初始化需要消化非常多的资源,或者生产对象很繁琐,可使用原型模式
 * 2.同一个对象有多个修改场景,一个对象需要提供给其他对象.
 *
 * 深拷贝和浅拷贝:
 * 浅拷贝:只复制基本数据类型和String(都是值传递),数组和引用对象都指向原生对象的地址
 * 深拷贝:对于引用对象和数组需要确保实现Cloneable,在拷贝的时候显示调用非基本类型的clone,
 * 依次类推,如果引用对象中的成员变量依然是个引用对象,那么就必须保证都实现了cloneable
 *
 * 串行化深复制:
 * transient(短暂的)只能修饰变量，而不能修饰方法和类,静态变量修饰无效
 * 修饰的变量将不再是对象持久化的一部分，变量按照java类变量默认值赋值，
 * 例如引用对象为null，基本变量为基本类型默认值
 *
 * Serializable 序列化
 *
 * hehe reset
 *
 *
 */
public class PrototypeClass implements Cloneable,Serializable {

    public transient int i=3;
    public transient String s;
    public transient SubType st;
    public transient static String sss="33";

    public PrototypeClass(int i, String s) {
        this.i = i;
        this.s = new String (s);
        st=null;
    }
    public PrototypeClass(int i, String s,SubType st) {
        this.i = i;
        this.s = new String (s);
        this.st=st;
    }

    @Override
    public PrototypeClass clone()  {
        PrototypeClass prototypeClass;
//        try {
//            prototypeClass =(PrototypeClass)super.clone();
//            prototypeClass.st=(SubType)this.st.clone();
//        }catch (CloneNotSupportedException e){
//            prototypeClass=null;
//        }
        prototypeClass=DeepCloneUtils.deepClone(this);
        return prototypeClass;
    }








}
