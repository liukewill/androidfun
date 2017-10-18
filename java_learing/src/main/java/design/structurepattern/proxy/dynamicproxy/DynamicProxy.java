package design.structurepattern.proxy.dynamicproxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//import sun.misc.ProxyGenerator;

/**
 * Created by kenan on 17/6/6.
 * 动态代理:运行时生成动态代理类,在方法调用时插入方法,面向切面编程AOP思想.
 * 动态类的生成主要用了反射,通过classloader,interface.class生成动态类
 * 动态类具体的实现根据java虚拟机编译原理,根据类信息拼接class文件.
 *
 * 动态代理factory,核心:通过JAVA API 反射获取动态代理instance
 * 以下两种方式:
 * JDK API:Proxy InvocationHandler 只能动态代理接口实现类
 * CGLIB:可代理任意class
 * Proxy.newProxyInstance(loader,interfaces,handler);
 *
 * InvocationHandler invoke具体方法
 *
 * 动态代理一般存在于框架中
 *
 * Proxy源码解析:
 * Proxy.newInstance  根据classloader interface信息拼接class字节码
 * 如下核心代码在内存中生成了字节码
 *  getProxyClass0  获取代理class入口
 *  proxyClassCache 缓存,针对同一接口的代理类只会有一个class
 *  ProxyClassFactory 真正的class生产者,生产细节包括class名称,接口方法等细节.
 *
 * byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
 * proxyName, interfaces, accessFlags);//拼接字节码
 *
 * 可通过java io将字节码文件输出,并反编译成java 可得到动态代理生成类
 * 动态代理类如下
 * public final class SubjectProxy extends Proxy implements ISubject{
 *     public SubjectProxy(InvocationHanlder handler)
 * }
 *
 *
 *
 */
public abstract class DynamicProxy  {

    public  <T> T newProxyInstance(T t){
        ClassLoader loader=t.getClass().getClassLoader();

        Class<?> []classes=t.getClass().getInterfaces();

        DynamicProxyHandler<T> handler= new DynamicProxyHandler<T>(t) {

            @Override
            public void beforeProxy(Object proxy, Method method, Object[] args) {
                DynamicProxy.this.beforeProxy(proxy,method,args);
            }

            @Override
            public void afterProxy(Object proxy, Method method, Object[] args) {
                DynamicProxy.this.afterProxy(proxy,method,args);
            }


        };


        return  newProxyInstance(loader,classes,handler);

    }

    public  static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler handler){
       //joinpoint接入点
        if(true){
            //创建代理前置message
        }
        return (T)Proxy.newProxyInstance(loader,interfaces,handler);
    }

    public abstract void beforeProxy(Object proxy, Method method, Object[] args);

    public abstract void afterProxy(Object proxy, Method method, Object[] args);

    /**
     * 根据类信息和提供的代理类名称,在内存中生成字节码,可通过java io输出,反编译后查看
     */
//    public  static void generateClassFile(Class clazz,String proxyName){
//
//        byte[] classFile= ProxyGenerator.generateProxyClass(proxyName,clazz.getInterfaces());
//        FileOutputStream outputStream=null;
//        try{
//            outputStream=new FileOutputStream("/Users/kenan/Desktop/"+proxyName+".class");
//            outputStream.write(classFile);
//            outputStream.flush();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void testFilePath(){
        File file=new File("a.txt");
        try {
            file.createNewFile();
            System.out.println(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
