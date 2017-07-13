package design.structurepattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by kenan on 17/6/9.
 */
public class ProxyHandler implements InvocationHandler {

    ISubject iSubject;
    public ProxyHandler(ISubject iSubject){
        this.iSubject=iSubject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("on-proxy");
        return method.invoke(iSubject,args);
    }
}
