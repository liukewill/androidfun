package design.structurepattern.proxy.forceProxy;

/**
 * Created by kenan on 17/5/10.
 */
public class Zzdemo {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        RealSubject realSubject=new RealSubject();
        design.structurepattern.proxy.forceProxy.AbsSubject proxy=realSubject.getProxy();
        proxy.doIt();

    }
}
