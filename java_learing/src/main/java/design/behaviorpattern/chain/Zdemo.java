package design.behaviorpattern.chain;


/**
 * Created by kenan on 17/6/2.
 * 责任链模式循环引用会导致StackOverFlowError 以下是stackoverflowerror的解释
 *
 * java虚拟机规范:定义了再虚拟机栈和本地方法栈中会产生stackoverflowerror
 * 虚拟机栈+本地方法栈=java内存栈
 * 每一个线程创建,java虚拟机会分配一块独立的内存区域  虚拟机栈+本地方法栈+程序计数器
 * 虚拟机栈中存储了方法执行时的相关信息,每个方法在调用时都会在虚拟机栈中创建一个方法帧
 * 方法帧中包含了局部变量,操作数,动态链接,方法出口等信息.
 * 本地方法栈和虚拟机栈基本相同,针对线程中的native方法(非java实现的方法)
 *
 * StackOverFlowError 当栈深度超过虚拟机分配给线程的栈大小时出现error
 * OutOfMemoryError 当再申请新内存时,虚拟机分配给线程的内存大小中无法再分配新的内存
 *
 * -Xss 虚拟机参数可以设置虚拟机分配给每个线程的内存大小
 * -Xmx -Xms JVM最大可用内存
 * -Xmn 年轻代大小
 * -Xss 设置每个线程的堆栈大小
 * 堆设置
 *-Xms:初始堆大小
 *-Xmx:最大堆大小
 *-XX:NewSize=n:设置年轻代大小
 *-XX:NewRatio=n:设置年轻代和年老代的比值。如:为3，表示年轻代与年老代比值为1：3，年轻代占整个年轻代年老代和的1/4
 *-XX:SurvivorRatio=n:年轻代中Eden区与两个Survivor区的比值。注意Survivor区有两个。如：3，表示Eden：Survivor=3：2，一个Survivor区占整个年轻代的1/5
 *-XX:MaxPermSize=n:设置持久代大小
 */
public class Zdemo {

    static int stacklenth=0;
    static int threadNum=0;

    public static void main(String[] args) {
        MyHandler h1=new MyHandler("h1");
        MyHandler h2=new MyHandler("h2");
        MyHandler h3=new MyHandler("h3");

        h1.setHandler(h2);
        h2.setHandler(h3);
//      h3.setHandler(h1); 循环引用产生stackoverflow error
//        h1.handler();

        try {
//            addStackLength();
            addNewThread();
        }catch (Throwable e){
            System.out.println("--------"+threadNum);
            e.printStackTrace();
        }
    }

    public static void m(){//递归方法+无线循环产生stackoverflow error 因为无限增加了栈深度
        while (true)
            m();
    }

    public static void addStackLength(){//无限增加栈深度导致stackoverflow
        stacklenth++;
        addStackLength();
    }

    public static void addNewThread(){//无限开线程导致oom
        while (true){
            threadNum++;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true);
                }
            }).start();
        }
    }


}
