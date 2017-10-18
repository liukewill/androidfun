package design.structurepattern.decorator;

import design.createpattern.builder.Pizza;

/**
 * Created by kenan on 17/5/10.
 */
public class ZzDemo {

    public static void main(String[] args) {
//        Component component=new ConcreteComponent();
//        ConcreteDecorator1 component1 =new ConcreteDecorator1(component);
//        component =new ConcreteDecorator1(component1);
//        component =new ConcreteDecorator2(component);
//        component.opreate();

        Pizza pizza=new Pizza.Builder()
                .setSize(10)
                .setbacon(false)
                .setChese(true)
                .build();

    }
}
