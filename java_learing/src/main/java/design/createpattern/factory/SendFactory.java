package design.createpattern.factory;

/**
 * Created by kenan on 17/2/13.
 * 工厂模式:创建一个工厂类,对同一个接口的实例类进行创建
 */
public class SendFactory {

    public enum Sender{
        ShunFeng,ZhongTong
    }

    public FactoryWork sendFactory(Sender type){

        FactoryWork factoryWork;
        switch (type){
            case ShunFeng:
                factoryWork=new ShunFengWork();
                break;

            case ZhongTong:
                factoryWork=new ZhongTongWork();
                break;

            default:
                factoryWork=new ShunFengWork();
        }

        return factoryWork;
    }

    public void send(Sender type){
        sendFactory(type).send();
    }

    public interface FactoryWork{
        void send();
    }
}
