package design.behaviorpattern.model;

import design.structurepattern.proxy.forceProxy.RealSubject;
import design.structurepattern.proxy.forceProxy.SubjectProxy;

/**
 * Created by kenan on 17/5/10.
 */
public class Zzdemo {
    public static void main(String[] args) {
        RealSubject realSubject=new RealSubject();
        SubjectProxy proxy=(SubjectProxy) realSubject.getProxy();
        proxy.doIt();
    }
}
