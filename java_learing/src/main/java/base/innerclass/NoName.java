package base.innerclass;

/**
 * Created by kenan on 17/8/4.
 */

public class NoName {
    public void finalize() {
        System.out.println("Free the occupied memory...");
    }

    public void printb(){

    }

    NoName noName=new NoName(){
        int a;
        int b;

        private void printa(){

        }

        @Override
        public void printb() {
            super.printb();
        }
    };




    class NoName1 extends NoName{

    }
}

