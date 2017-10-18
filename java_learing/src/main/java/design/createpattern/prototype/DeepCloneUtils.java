package design.createpattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

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

    /**
     * 对象赋值，浅拷贝
     * 反射赋值
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(T class1,T class2){
        Class<?> clazz1 = class1.getClass();
        Class<?> clazz2 = class2.getClass();
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        for (int i = 0; i < fields1.length; i++) {
            try {
                fields1[i].setAccessible(true);
                fields2[i].setAccessible(true);
                Object obg1 = fields1[i].get(class1);
                Object obg2 = fields2[i].get(class2);

                fields1[i].set(class1, fields2[i].get(class2));//浅拷贝
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return class1;
    }
    /**
     * 对象赋值 深拷贝
     * 由于序列化耗时，如对象赋值均为值引用，建议使用copyProperties()
     * @param <T>
     * @return
     */
    public static <T> T deepCopyProperties(T class1,T class2){
        Class<?> clazz1 = class1.getClass();
        Class<?> clazz2 = class2.getClass();
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        for (int i = 0; i < fields1.length; i++) {
            try {
                fields1[i].setAccessible(true);
                fields2[i].setAccessible(true);
                Object obg1 = fields1[i].get(class1);
                Object obg2 = fields2[i].get(class2);

                fields1[i].set(class1, deepClone(fields2[i].get(class2)));//深拷贝
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return class1;
    }
}
