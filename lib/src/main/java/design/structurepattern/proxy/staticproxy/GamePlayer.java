package design.structurepattern.proxy.staticproxy;

/**
 * Created by kenan on 17/6/5.
 * 代理模式 对其他对象提供代理,控制对这个对象的访问.状态,策略,访问者,装饰者基于代理模式
 * 如果同时存在代理和被代理,有时需要限制可访问权限,分为普通代理和强制代理
 *
 * 普通代理模式:代理->被代理  可直接访问代理,不可直接访问被代理  通过构造函数限制被代理创建条件
 * 强制代理模式:被代理->代理  被代理角色自己控制代理,无代理不工作,需要显示设置代理.比较特殊
 */
public class GamePlayer implements IGame {

    public GamePlayer(){

    }

    public GamePlayer(IGame proxy)throws Exception{
        if(proxy==null){//限制可创建对象条件
            throw new Exception("IGame proxy must not null");
        }else if(!proxy.getClass().getName().contains("Proxy")){
            throw new Exception("Only proxy can create gameplayer!");
        }
    }

    @Override
    public void login() {
        System.out.println("login success");
    }

    @Override
    public void upgrade() {
        System.out.println("ugrade success");
    }
}
