package design.structurepattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by kenan on 17/6/5.
 * 一般一个proxy 对应一个handler 在方法invoke时候执行具体代理规则
 */
public abstract class DynamicProxyHandler<T> implements InvocationHandler {


    Class cls=null;//被代理class

    T obj=null;//被代理对象

    public DynamicProxyHandler(T o){
        this.obj=o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeProxy(proxy,method,args);
        Object result=method.invoke(this.obj,args);//对真实方法的调用
        afterProxy(proxy,method,args);
        return result;
    }

    public abstract void afterProxy(Object proxy, Method method, Object[] args);

    public abstract void beforeProxy(Object proxy, Method method, Object[] args);

}
