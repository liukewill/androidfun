package design.structurepattern.proxy.dynamicproxy;


import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by kenan on 17/5/10.
 */
public class Zzdemo {
    public static void main(String[] args) {
        //文件输出内存中生成的代理类开关,对应源码中的ProxyGenerator.saveGeneratedFiles
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Rsubject rsubject = new Rsubject();
        ProxyHandler proxyHandler=new ProxyHandler(rsubject);
        ISubject subjectProxy=DynamicProxy.newProxyInstance(rsubject.getClass().getClassLoader(),
                rsubject.getClass().getInterfaces(),proxyHandler );
        subjectProxy.doIt("hah");

        testProxy();
        testAndorid();

    }


    /**
     * 文件输出$Proxy0.Class
     */
//    private static void generateProxyClassFile() {
//        byte[] classFile = sun.misc.ProxyGenerator.generateProxyClass(
//                "$MyProxy", ISubject.class.getInterfaces());
//
//        FileOutputStream out = null;
//        String path = "/Users/ghj/startup/$MyProxy.class";
//        try {
//            out = new FileOutputStream(path);
//            out.write(classFile);
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static void  testProxy(){
        Rsubject rsubject = new Rsubject();
        ISubject iSubject = new DynamicProxy() {
            @Override
            public void beforeProxy(Object proxy, Method method, Object[] args) {

                if(method.getName().equalsIgnoreCase("doIt")){
                    System.out.println("befor-do-it"+proxy.getClass().getName());

                }

                if(args[0] instanceof String){
                    System.out.println("METHOD-"+method.getName()+"***PARAMS-"+args[0]);
                }

            }

            @Override
            public void afterProxy(Object proxy, Method method, Object[] args) {
                if(method.getName().equalsIgnoreCase("doIt")){
                    System.out.println("after-do-it");
                }
            }
        }.newProxyInstance(rsubject);
        ProxyHandler proxyHandler=new ProxyHandler(rsubject);
        ISubject proxySubject=DynamicProxy.newProxyInstance(rsubject.getClass().getClassLoader(),rsubject.getClass().getInterfaces(),proxyHandler);
        proxySubject.doIt("22");
    }

    private static void testAndorid(){
        RAndroid rAndroid=new RAndroid();
        IAndroid dynamicProxy=new DynamicProxy(){
            @Override
            public void beforeProxy(Object proxy, Method method, Object[] args) {

            }

            @Override
            public void afterProxy(Object proxy, Method method, Object[] args) {

            }
        }.newProxyInstance(rAndroid);
    }

}
