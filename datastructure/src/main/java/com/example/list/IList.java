package com.example.list;

/**
 * Created by kenan on 18/3/5.
 */

public interface IList<E>{

    int size();

    boolean isEmpty();

    void add(E e);

    void add(int i,E e);

    void remove(E e);

    void remove(int i);


}
