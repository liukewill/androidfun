package thread;

/**
 * Created by kenan on 18/3/15.
 */

public class Tv {

    public  boolean open=false;

    public  String name="";

    public Tv(String name){
        this.name=name;
    }

    public  boolean openTv(){
        System.out.println(name+"电视状态---"+open);

        open=true;

        System.out.println(name+"电视已打开---"+open);
        return open;
    }

    public  boolean closeTv(){
        System.out.println(name+"电视状态---"+open);

        open=false;

        System.out.println(name+"电视已关闭---"+open);
        return open;
    }

    public void setName(String name){
        this.name=name;
    }

}
