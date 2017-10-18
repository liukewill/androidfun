package base.innerclass;

public class TestJc {
    public void Test() throws InterruptedException {
        System.out.println("Start of program.");

        new NoName() {};
        System.gc();

        new NoName() {};
        NoName n=new NoName() {};


        System.out.println("End of program.");

        Thread.sleep(5000);

        System.out.println("End of Thread.");
    }

    public static void main(String args[]) throws InterruptedException {
        new TestJc().Test();
    }
}
