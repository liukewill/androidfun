package design.structurepattern.decorator;

/**
 * Created by kenan on 17/5/10.
 * 具体构件
 */
public  class ConcreteComponent implements Component {

    @Override
    public void opreate() {
        System.out.println("Concrete");
    }
}
