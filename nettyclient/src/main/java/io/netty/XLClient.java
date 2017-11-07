package io.netty;

import android.util.Log;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by kenan on 17/11/6.
 */

public class XLClient {
    public static String TAG="XLClient";
    public static final int port =8060;
    public static final String host ="http://10.19.160.27";
    public static final int HEART_BEAT_TIME=20;

    private static final NioClientSocketChannelFactory clientSocketChannelFactory=new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
    private static final ClientBootstrap clientBootstrap=new ClientBootstrap(clientSocketChannelFactory);
    private static ScheduledExecutorService mScheduledExecutorService;
//    static {
//        // 自定义心跳，每隔20秒向服务器发送心跳包
//        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, HEART_BEAT_TIME, HEART_BEAT_TIME, TimeUnit.SECONDS);
//    }


    public static void start(){
        try {
            ChannelFuture future = XLClient.startup();
            Log.i(TAG,"future state is "+future.isSuccess());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void startHearBeat(){

    }
    /**
     * 启动客户端
     * @return
     * @throws Exception
     */
    public static ChannelFuture startup() throws Exception {
        /**
         * 注意：由于XLClientHandler中有状态的成员变量，因此不能采用默认共享ChannelPipeline的方式
         * 例如，下面的代码形式是错误的：
         * ChannelPipeline pipeline=clientBootstrap.getPipeline();
         * pipeline.addLast("handler", new XLClientHandler());
         */
        clientBootstrap.setPipelineFactory(new XLClientPipelineFactory()); //只能这样设置
        /**
         * 请注意，这里不存在使用“child.”前缀的配置项，客户端的SocketChannel实例不存在父级Channel对象
         */
        clientBootstrap.setOption("tcpNoDelay", true);
        clientBootstrap.setOption("keepAlive", true);

        ChannelFuture future=clientBootstrap.connect(new InetSocketAddress(host, port));
        /**
         * 阻塞式的等待，直到ChannelFuture对象返回这个连接操作的成功或失败状态
         */
        future.awaitUninterruptibly();
        /**
         * 如果连接失败，我们将打印连接失败的原因。
         * 如果连接操作没有成功或者被取消，ChannelFuture对象的getCause()方法将返回连接失败的原因。
         */
        if (!future.isSuccess()) {
            future.getCause().printStackTrace();
        }else {
            Log.i(TAG,"client is connected to server "+host+":"+port);
        }
        return future;
    }

    /**
     * 关闭客户端
     * @param future
     * @throws Exception
     */
    public static void shutdown(ChannelFuture future) throws Exception {
        try {
            /**
             * 主动关闭客户端连接，会阻塞等待直到通道关闭
             */
            future.getChannel().close().awaitUninterruptibly();
            //future.getChannel().getCloseFuture().awaitUninterruptibly();
            /**
             * 释放ChannelFactory通道工厂使用的资源。
             * 这一步仅需要调用 releaseExternalResources()方法即可。
             * 包括NIO Secector和线程池在内的所有资源将被自动的关闭和终止。
             */
            clientBootstrap.releaseExternalResources();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,e.getMessage());
        }
        finally{
            System.exit(1);
            Log.i(TAG,"client is shutdown to server "+host+":"+port);
        }
    }
}
