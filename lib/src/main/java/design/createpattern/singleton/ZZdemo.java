package design.createpattern.singleton;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import design.createpattern.prototype.DeepCloneUtils;

/**
 * Created by kenan on 17/9/16.
 */

public class ZZdemo {

    public static void main(String []args){
        test();
    }

    private static void testSingle(){
        Singleton singleton=Singleton.getInstance();
        System.out.println(singleton.name);

        try {
            Class singletonClass1=Class.forName("design.createpattern.singleton.Singleton");
            Constructor constructor=singletonClass1.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            Singleton singleton1=(Singleton)constructor.newInstance("WORLD");
            System.out.println(singleton1.name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testSubSingle(){
        SubSingle subSingle=new SubSingle("33");
        SubSingle subSingle2=new SubSingle("44");
    }

    private static void testDeepClone(){
        Singleton singleton=Singleton.getInstance();
        Singleton singleton2= DeepCloneUtils.deepClone(singleton);
        singleton.name="333";
    }

    private static void test(){
        List<String> list=new ArrayList<>();
        list.add("1");

        List list1=new ArrayList<String>();

        list1.add("2");
        list1.add(2);
        list1.add(2.0f);

        for (Object o : list1) {
            System.out.println(o.toString());
        }

        list=new LinkedList();

        list.add("b");


    }

}
