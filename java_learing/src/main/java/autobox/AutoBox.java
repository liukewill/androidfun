package autobox;

/**
 * Created by kenan on 17/12/16.
 */

public class AutoBox {


    private static  int test(){
        Integer integer=3;
        Integer integer1=3;

        Integer integer2=300;
        Integer integer3=300;
        return integer;
    }


    public static void main(String s[]){

        Integer integer=3;
        Integer integer1=3;

        Integer integer2=300;
        Integer integer3=300;

        System.out.println(integer==integer1);
        System.out.println(integer2==integer3);
    }
}
