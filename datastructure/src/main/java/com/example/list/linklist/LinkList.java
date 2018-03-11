package com.example.list.linklist;

import java.util.LinkedList;

/**
 * Created by kenan on 18/3/5.
 * @param <D>
 */

public  class LinkList<D>{
        Node<D> head = null;
        Node<D> last = null;
        int size;

    /**
     * 双向链表
     * @param <D>
     */
    public static class Node<D> {
        public D emement;
        public Node nextNode;
        public Node prevNode;

        Node(D element){
            this.emement=element;
            this.nextNode=null;
        }

        Node(Node prevNode,D e,Node nextNode){
            this.emement=e;
            this.nextNode=nextNode;
            this.prevNode=prevNode;
        }


    }

    public LinkList(){
    }

    public void add(D d){
        if(head==null){
            head=new Node(null,d,null);
            last =head;
        }else{
            Node newNode=new Node(last,d,null);
            last.nextNode=newNode;
            last=newNode;
        }

        size++;
    }

    /**
     * 遍历 指针移动 时间复杂度O(n)
     * 源码中获取size 在add和remove时候 ++ -- 控制 无需遍历
     * @return
     */
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

    public Node<D> findNode(int index){
        if(index==0){
            return head;
        }
        int count=0;
        if(index<=size>>1){
            Node<D> now=head;
            for(int i=0;i<index;i++){
                now=now.nextNode;
            }
            return now;
        }else{
            Node<D> now=last;
            for(int i=size;i>index;i--){
                now=now.prevNode;
            }
            return now;
        }
    }

    public D get(int index){
        return findNode(index).emement;
    }

    public void remove(int index){
        Node node=findNode(index);
        Node prev=node.prevNode;
        Node next=node.nextNode;

        prev.nextNode=next;
        next.prevNode=prev;
        size--;

    }

    private int index(D d){
        int index=0;

        Node nd=head;
        while (nd!=null){
            index++;
            if(d.equals(nd.emement)){
                return index;
            }
            nd=nd.nextNode;
        }
        return -1;
    }

    public static void main(String s[]){

        LinkList<String> ls=new LinkList<>();
        for (int j = 0; j < 10; j++) {
            ls.add(j+1+"");
        }
        ls.remove(4);
        for (int j = 0; j < ls.getLength(); j++) {
            System.out.println(ls.get(j));
        }
    }

    /**
     * 求倒数第K个节点
     * 先移动K个
     * 然后整体移动到末尾
     * @param index
     * @return
     */
    private Node<D> getLastK(int index){
        Node<D> node1=head;
        Node<D> node2=head;

        for(int i=0;i<index;i++){
            node2=node2.nextNode;
        }
        int num=0;
        while(node2!=null){
            num++;
            node1=node1.nextNode;
            node2=node2.nextNode;
        }
        System.out.println(num);
        return node1;
    }
}


