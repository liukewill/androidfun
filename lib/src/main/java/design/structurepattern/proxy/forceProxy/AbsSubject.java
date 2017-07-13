package design.structurepattern.proxy.forceProxy;

/**
 * Created by kenan on 17/6/5.
 */
public abstract class AbsSubject {

    private AbsSubject proxy;

    protected abstract void doIt () ;

    public AbsSubject getProxy() {
        if(proxy==null){
            proxy=new SubjectProxy(this);
        }
        return proxy;
    }

    public boolean hasProxy(){
        return this.proxy==null?false:true;
    }
}
