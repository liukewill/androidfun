package com.example.list.linklist;

/**
 * Created by kenan on 18/3/5.
 */

public class Node<E> {
    E emement;
    Node nextNode;

    Node(E element){
        this.emement=element;
        this.nextNode=null;
    }


}
