package design.behaviorpattern.chain;


/**
 * Created by kenan on 17/6/2.
 * 责任链模式:有多个对象,每个对象持有下一个对象的引用,形成一条链,请求再这条链上传递,知道某一对象决定处理该请求.
 * 但是发出者并不清楚到底最终那个对象会处理该请求,所以责任链模式可以实现隐瞒客户端的情况下,对系统进行动态调整.
 */
public abstract class AbsHandler implements IHandler {

    private IHandler handler;


    public IHandler getHandler() {
        return handler;
    }

    public void setHandler(IHandler handler) {
        this.handler = handler;
    }


}
