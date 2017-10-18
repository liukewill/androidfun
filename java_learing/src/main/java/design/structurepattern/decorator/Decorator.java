package design.structurepattern.decorator;

/**
 * Created by kenan on 17/5/10.
 * 装饰者模式 动态扩展类功能,可动态撤销
 */
public  class Decorator implements Component {

    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void opreate() {
        this.component.opreate();
    }
}
