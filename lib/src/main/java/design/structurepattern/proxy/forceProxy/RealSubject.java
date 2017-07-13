package design.structurepattern.proxy.forceProxy;

/**
 * Created by kenan on 17/6/5.
 */
public class RealSubject extends AbsSubject {


    @Override
    protected void doIt(){
        if(hasProxy()){
            System.out.print("RealSubject-DO-IT!");
        }else{
            System.out.print("Please call me proxy!");
        }
    }

}
