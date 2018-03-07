package com.example;

import java.util.ArrayList;

public class MyClass {

    public static void main(String [] args){
        byte a=24;

        Object []ss={1,"b",3L};
        ;
        for (Object s : ss) {
            System.out.println(s.hashCode());
        }

    }
}
