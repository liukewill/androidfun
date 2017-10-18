package design.structurepattern.decorator;

/**
 * Created by kenan on 17/5/10.
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    private void conrete2(){
        System.out.println("concrete2");
    }

    @Override
    public void opreate() {
        super.opreate();
        conrete2();
    }

    public void haha(){

    }
}
