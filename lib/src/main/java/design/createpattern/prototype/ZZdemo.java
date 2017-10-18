package design.createpattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenan on 17/6/16.
 */
public class ZZdemo {

    Inner inner = new Inner();

    public class Inner {

    }

    private void aa(G<Integer> a){

    }

    public static void main(String[] args) {
//       SubType subType1=new SubType("11",1);
//       SubType subType2=new SubType("22",2);
//        subType2=DeepCloneUtils.copyProperties(subType2,subType1);
//
//        ZZdemo zZdemo=new ZZdemo();

        List<String> s = new ArrayList<>();
        List<Integer> i = new ArrayList<>();

        G<String> AA=new G<>();
        G<Integer> BB=new G<>();

        AA.a=1;
        BB.a=2;

        System.out.print(AA.a);


    }



    public static class G<T>{
        public static int a;
    }
}
