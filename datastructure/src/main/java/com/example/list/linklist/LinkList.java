package com.example.list.linklist;

/**
 * Created by kenan on 18/3/5.
 */

public  class LinkList<D>{
    Node<D> head=null;
    Node<D> tail=null;

    public LinkList(){
    }

    public void add(D d){
        if(head==null){
            head=new Node(d);
            tail=head;
        }else{
            tail.nextNode=new Node(d);
            tail=tail.nextNode;
        }
    }

    public int getLength(){
        int size=0;
        if(head==null){
            size=0;
        }else{
            Node current=head;
            while (current!=null){
                current= current.nextNode;
                size++;
            }
        }
        return  size;
    }

    public static void main(String s[]){
        LinkList<String> ll=new LinkList<>();
        ll.add("1");
        ll.add("33");
        System.out.println(ll.getLength());
    }
}


