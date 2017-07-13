package design.createpattern.factory;

/**
 * Created by kenan on 17/2/13.
 */
public class ZhongTongWork implements design.createpattern.factory.SendFactory.FactoryWork {
    public static final String TAG="ZhongTong";

    @Override
    public void send() {
        System.out.println(TAG);
    }
}
