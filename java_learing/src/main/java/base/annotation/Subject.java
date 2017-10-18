package base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by kenan on 17/6/12.
 * 四个元注解,修饰注解的注解
 * 
 @Documented –注解是否将包含在JavaDoc中
 @Retention –什么时候使用该注解
 @Target? –注解用于什么地方
 @Inherited – 是否允许子类继承该注解
 *
 * @Target 修饰对象范围 取值ElementType
 * CONSTRUCTOR 描述构造器
 * FIELD 描述成员变量 域
 * LOCAL_VARIABLE 局部变量
 * METHOD 方法
 * PACKAGE 包
 * PARAMETER 参数
 * TYPE 类,接口,注解自身,枚举
 * ANNOTATION_TYPE 修饰其他注释
 *
 * @Retention 保留有效期,注解的生命周期限制 取值RetentionPolicy meta-annotation
 * SOURCE .java文件中保留 编译器忽略 java编译成class时候 注解被遗弃
 * CLASS .class文件中保留 虚拟机忽略 jvm加载class文件时被遗弃,默认生命周期
 * RUNTIME 运行时保留 可通过反射获取注解的属性值 不影响class执行,annotation与class使用上是分离的
 *
 * @Documented 标记注解,表示可被javadoc文档化
 *
 * @Inherited 这个被标注的类型是被继承的 ,使用了该类型的注解的class,该annotation也被用于该class的子类
 *
 * 三个java内建注解
 * @Override 复写 SOURCE
 *
 * @Deprecated 方法不建议使用 RUNTIME
 *
 * @SuppressWarnings 忽略特定的警告信息 SOURCE
 *
 * 自定义注解
 *
 * 注注解中所有的属性被定义成方法，并允许提供默认值。
 *
 * 修饰符只能是public 和default
 *
 * 只有一个参数直接使用value(),申明直接赋值
 *
 * 注解参数
 *     1.所有基本数据类型（int,float,boolean,byte,double,char,long,short)
　　　　2.String类型
　　　　3.Class类型
　　　　4.enum类型
　　　　5.Annotation类型
　　　　6.以上所有类型的数组


 */
@SubjectAnnotation(subjectName = "Subject",priority = SubjectAnnotation.Priority.HIGH)
public class Subject {

    @SubjectAnnotation(subjectName = "doSomething",priority = SubjectAnnotation.Priority.MEDIUM)
    public void doSomething(){

    }
}
