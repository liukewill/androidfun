package design.structurepattern.proxy.dynamicproxy;

/**
 * Created by kenan on 17/6/5.
 */
public class Rsubject implements ISubject {
    @Override
    public void doIt(String s) {
        System.out.println("R-do-it--"+s);
    }
}
