package thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kenan on 18/3/14.
 */

public class SingleThread {

    public static void main (String[]args){
        ExecutorService singleThread= Executors.newScheduledThreadPool(10000);

        singleThread.submit(r);

        singleThread.submit(r);

        singleThread.submit(r);

        singleThread.submit(r);

        singleThread.submit(r);

        singleThread.submit(r);

        singleThread.submit(r);


        singleThread.submit(r);

        singleThread.submit(r);
        singleThread.submit(r);
        singleThread.submit(r);
        singleThread.submit(r);
        singleThread.submit(r);
        singleThread.submit(r);
        singleThread.submit(r);
        singleThread.submit(r);














    }

    static Runnable r=new Runnable() {
        @Override
        public void run() {
            System.out.println("当前线程ID:"+Thread.currentThread());
        }
    };
}
