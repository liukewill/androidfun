package design.createpattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by kenan on 17/6/16.
 */
public class ZZdemo {

    public static void main(String []args){
        try {
            Externaliza ez=new Externaliza();

            Externaliza eee=DeepCloneUtils.deepClone(ez);

            System.out.println(eee.s);
        }catch (Exception e){

        }



    }
}
