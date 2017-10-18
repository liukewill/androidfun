package design.createpattern.factory;

/**
 * Created by kenan on 17/2/13.
 */
public class ShunFengWork implements design.createpattern.factory.SendFactory.FactoryWork {

    public static final String TAG="ShunFeng";

    @Override
    public void send() {
        System.out.println(TAG);
    }
}
