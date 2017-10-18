package base.genric;

/**
 * Created by kenan on 17/5/10.
 */
public class Zzdemo {
    public static void main(String[] args) {

        Child child= new Child<String>() {
            @Override
            public void hello(String s) {

            }
        };


        Sunzi sunzi=new Sunzi();
        String dd=sunzi.getT();
        Child<String> s=new Child<String>() {
            @Override
            public void hello(String s) {

            }
        };

        String BB=s.getT();


    }
}
