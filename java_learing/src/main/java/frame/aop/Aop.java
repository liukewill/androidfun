package frame.aop;

/**
 * Created by kenan on 17/6/10.
 * AOP
 * 定义:Aop (aspect orient programming) 面向切面编程,OOP面向对象编程的补充
 *
 * 用途:用于统一处理一些横切性质的系统级服务,如事务管理,日志,安全检查,缓存,对象池管理
 *
 * 原理:基于设计模式中的代理模式:静态代理与动态代理
 *
 * 实现:Aop框架生成代理类的方式.分为静态代理与动态代理框架
 * 静态代理框架:在编译过程中,使用aop框架提供的命令进行编译,在编译阶段生成Aop代理类,称为编译时增强. 代表如:Aspectj框架
 * 动态代理框架:在运行过程中,根据jdk 动态代理或者cglib在内存中临时生成代理类class成为运行时增强.代表如:spring框架
 *
 * CGLib vs JDK PROXY
 * CGLib创建的动态代理对象性能比JDK创建的动态代理对象的性能高不少，
 * 但是CGLib在创建代理对象时所花费的时间却比JDK多得多，
 * 所以对于单例的对象，因为无需频繁创建对象，用CGLib合适，
 * 反之，使用JDK方式要更为合适一些。
 * 同时，由于CGLib由于是采用动态创建子类的方法，对于final方法，无法进行代理。
 * JDK 只能代理接口
 *
 *
 *
 */
public class Aop {
}
