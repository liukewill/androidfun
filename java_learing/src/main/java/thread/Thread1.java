package thread;

import com.oracle.tools.packager.Log;

/**
 * Created by kenan on 18/3/11.
 */

public class Thread1 extends Thread {

    public static int a=0;

    private String name;

    public Thread1(String name) {
        this.name = name;
    }

    public Thread1(String name,Runnable r){
        super(r);
        this.name=name;
    }

    public void run() {
        super.run();
    }

    public static class R1 implements Runnable{
        String name;
        public R1(String name){
            this.name=name;
        }
        @Override
        public void run() {
            for (int j = 0; j <10 ; j++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name+"："+"你是猪");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        float f=1.2f;
        for(float y = (float) f;y>-f;y -=0.11)
        {
            for(float x= (float) -f;x<f;x+= 0.05)
            {
                float a = x*x+y*y-1;
                if((a*a*a-x*x*y*y*y)<=0.0)
                {
                    Thread.sleep(6);

                    System.out.print("*");
                }
                else {
                    Thread.sleep(8);
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }

        System.out.println("                 I LOVE U ~ YQQ");
        Thread.sleep(5000);
    }




}
