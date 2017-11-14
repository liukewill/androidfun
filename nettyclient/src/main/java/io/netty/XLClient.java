package com.baidu.lbs.services.socket;

import android.util.Log;

import com.baidu.lbs.commercialism.login.LoginManager;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kenan on 17/11/6.
 *
 * 长连接客户端流程：
 * 1.连接
 * 2.授权
 * 3.心跳
 * 4.新单
 *
 * 长连接操作：
 * 1.connect
 * 2.startHeartBeat
 * 3.sendMsgToServer
 * 4.sendHeartBeat
 * 5.
 */

public class XLClient {
    public static final String TAG="NettyClient";
    public static final String HOST ="10.19.160.27";
    public static final int PORT =8060;
    public static final int HEART_BEAT_TIME=10;

    private boolean isConnect = false;
    private ChannelFuture future;
    private Channel channel;

    private  NioClientSocketChannelFactory clientSocketChannelFactory;
    private  ClientBootstrap clientBootstrap;
    private  ScheduledExecutorService mScheduledExecutorService;
    private  ExecutorService singleThreadExecutor=Executors.newSingleThreadExecutor();
    private static NettyClient nettyClient =new NettyClient();

    private NettyClient(){

    }

    public static NettyClient getInstance(){
        return nettyClient;
    }

    public void start(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"run-"+ System.currentTimeMillis());
                startProcess();
            }
        };

        singleThreadExecutor.execute(runnable);
    }

    private  void startProcess(){
        try {
            clientSocketChannelFactory=new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
            clientBootstrap=new ClientBootstrap(clientSocketChannelFactory);

            ChannelFuture future = connect();
            Log.i(TAG,"future state is "+future.isSuccess());
            channel=future.getChannel();
            if(future.isSuccess()){
                sendAuth();
                startHeartBeat();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reconnect(){
        try {

            shutdown(future);
            while(true){
                if(!future.isSuccess()){
                    break;
                }
            }
            startProcess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown(){
        try {
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService=null;
            shutdown(future);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private   void startHeartBeat(){
//         自定义心跳，每隔20秒向服务器发送心跳包
        if(isConnect()) {
            if (mScheduledExecutorService == null) {
                mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            }

            mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "send-hb");
                    sendHeartBeat();
                }
            }, HEART_BEAT_TIME, HEART_BEAT_TIME, TimeUnit.SECONDS);
        }else{
        }


    }
    /**
     * 启动客户端
     * @return
     * @throws Exception
     */
    private  synchronized ChannelFuture connect() throws Exception {
        if(!isConnect()) {

            /**
             * 注意：由于XLClientHandler中有状态的成员变量，因此不能采用默认共享ChannelPipeline的方式
             * 例如，下面的代码形式是错误的：
             * ChannelPipeline pipeline=clientBootstrap.getPipeline();
             * pipeline.addLast("handler", new NettyClientHandler());
             */
            clientBootstrap.setPipelineFactory(new NettyClientPipelineFactory(nettyHandlerListener)); //只能这样设置
            /**
             * 请注意，这里不存在使用“child.”前缀的配置项，客户端的SocketChannel实例不存在父级Channel对象
             */
            clientBootstrap.setOption("tcpNoDelay", true);
            clientBootstrap.setOption("keepAlive", true);

            future = clientBootstrap.connect(new InetSocketAddress(HOST, PORT));
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
                isConnect = false;
            } else {
                Log.i(TAG, "client is connected to server " + HOST + ":" + PORT);
                isConnect = true;
            }
        }
        return future;
    }

    /**
     * 关闭客户端
     * @param future
     * @throws Exception
     */
    public  void shutdown(ChannelFuture future) throws Exception{
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
            Log.i(TAG,"client is shutdown to server "+ HOST +":"+ PORT);
        }
    }


    public boolean sendMsgToServer(ChannelBuffer channelBuffer, ChannelFutureListener listener) {
        boolean flag = channel != null && isConnect;
        if(flag)
            channel.write(channelBuffer).addListener(listener);
        return flag;
    }

    public boolean isConnect() {
        if(channel==null){
            return false;
        }
        return  channel.isConnected();
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public void sendHeartBeat(){
        if(!isConnect())
            return;

        NettyRequest nettyRequest=NettyRequestFactory.getHeartBeatRequest();
        ChannelBuffer channelBuffer=NettyProtocolUtil.encodeRequest(NettyRequestFactory.ENCODE.UTF_8, nettyRequest);
        Log.i(NettyClient.TAG,nettyRequest.toString());
        sendMsgToServer(channelBuffer, new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    Log.i(TAG,"HB-success"+channelFuture.getChannel().getRemoteAddress());

                }else{
                    Log.i(TAG,"HB-fail"+channelFuture.getChannel().getRemoteAddress());

                }
            }
        });
    }

    public void sendAuth(){
        NettyRequest nettyRequest=NettyRequestFactory.getAuthRequest();
        ChannelBuffer channelBuffer=NettyProtocolUtil.encodeRequest(NettyRequestFactory.ENCODE.UTF_8, nettyRequest);
        Log.i(NettyClient.TAG,nettyRequest.toString());
        sendMsgToServer(channelBuffer, new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    Log.i(TAG,"AUTH-success"+channelFuture.getChannel().getRemoteAddress());

                }else{
                    Log.i(TAG,"AUTH-fail"+channelFuture.getChannel().getRemoteAddress());
                }
            }
        });
    }



    private NettyClientHandler.NettyHandlerListener nettyHandlerListener=new NettyClientHandler.NettyHandlerListener() {
        @Override
        public void onSuccess(ChannelHandlerContext ctx, MessageEvent e) {
            Log.i(TAG,"success");
        }

        @Override
        public void onError(ChannelHandlerContext ctx, ExceptionEvent e) {
            Log.i(TAG,"error");

        }

        @Override
        public void onClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
            Log.i(TAG,"closed");
            setConnect(false);
        }
    };




}
