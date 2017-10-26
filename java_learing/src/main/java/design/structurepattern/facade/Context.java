package design.structurepattern.facade;

/**
 * Created by kenan on 17/10/26.
 * 外观模式非常的简单，只是封装了子系统的操作，并且暴露接口让用户使用，
 * 避免了用户需要与多个子系统进行交互，降低了系统的耦合度、复杂度。
 * 如果没有外观模式的封装，那么用户就必须知道各个子系统的相关细节，
 * 子系统之间的交互必然造成纠缠不清的关系，影响系统的稳定性、复杂度。
 *
 *
 * Context
 *  app启动，
 *  1.fork子进程
 *  2.执行ActivityThread.main方法，启动该进程
 *  3.
 */

public class Context {
}
