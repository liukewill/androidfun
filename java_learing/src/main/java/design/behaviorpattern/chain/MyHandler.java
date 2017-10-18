package design.behaviorpattern.chain;

/**
 * Created by kenan on 17/6/2.
 */
public class MyHandler extends AbsHandler {

    private String name;

    public MyHandler(String name) {
        this.name = name;
    }

    @Override
    public void handler() {
        System.out.println(name);
        if(getHandler()!=null){
            getHandler().handler();
        }
    }
}
