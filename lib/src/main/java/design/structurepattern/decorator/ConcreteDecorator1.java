package design.structurepattern.decorator;

/**
 * Created by kenan on 17/5/10.
 */
public class ConcreteDecorator1 extends Decorator {


    public ConcreteDecorator1(design.structurepattern.decorator.Component component) {
        super(component);
    }


    private void conrete1(){
        System.out.println("concrete1");
    }

    @Override
    public void opreate() {
        conrete1();
        super.opreate();
    }
}
