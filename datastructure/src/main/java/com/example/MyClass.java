package com.example;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class MyClass {

    public static void main(String arg[]){
        List list=new ArrayList();
        list.add(null);

        TreeSet set=new TreeSet<>();
        set.add(new String("22"));
        set.add("22");
        set.add("22");
        set.add(new String("33"));
        set.size();
    }


}
