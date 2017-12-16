package autobox;

/**
 * Created by kenan on 17/12/16.
 */

public class AutoBox {


    private static  int test(){
        Integer integer=3;
        return integer;
    }


    public static void main(String s[]){
        int a=test();
        Integer integer=test();
        System.out.println(integer.byteValue());
    }
}
