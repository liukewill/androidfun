package design.structurepattern.proxy.forceProxy;

/**
 * Created by kenan on 17/6/5.
 */
public class SubjectProxy extends AbsSubject {

    private AbsSubject absSubject;

    public SubjectProxy(AbsSubject absSubject){
        this.absSubject=absSubject;
    }

    @Override
    public void doIt()  {
        this.absSubject.doIt();
    }
}
