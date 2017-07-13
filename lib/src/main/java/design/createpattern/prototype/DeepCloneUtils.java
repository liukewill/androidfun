package design.createpattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by kenan on 17/6/19.
 */
public class DeepCloneUtils {

    public static <T> T deepClone(T t){
        try {
            //写入流
            ByteArrayOutputStream bo=new ByteArrayOutputStream();
            ObjectOutputStream oo=new ObjectOutputStream(bo);
            oo.writeObject(t);

            ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi=new ObjectInputStream(bi);
            return (T)oi.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
