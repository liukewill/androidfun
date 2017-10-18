package frame.aop.cglib;

import net.sf.cglib.proxy.Enhancer;


/**
 * Created by kenan on 17/6/11.
 */
@SuppressWarnings("unchecked")
public class ZZdmeo {
    public static void main(String[] args) {
        Enhancer en = new Enhancer();
        // 设置要代理的目标类
        en.setSuperclass(Subject.class);
        // 设置要代理的拦截器
        en.setCallback(new SubjectInterceptor());
        // 生成代理类的实例

        Subject subject=(Subject)en.create();

        subject.hello();

        subject.world();
    }
}
