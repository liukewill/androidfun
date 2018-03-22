package thread;

/**
 * Created by kenan on 18/3/15.
 */

public class Main {

    public static void main(String []args){
        Tv tv1=new Tv("三星1");
        Tv tv2=new Tv("小米");
        tv2.setName("三星2");

        tv1.openTv();
        tv1.closeTv();
        tv2.openTv();

    }
}
