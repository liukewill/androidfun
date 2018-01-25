package com.kenan.socket;

import android.util.Log;

import com.baidu.lbs.commercialism.app.DuApp;
import com.baidu.lbs.comwmlib.timer.TimerSchedule;
import com.baidu.lbs.net.type.SwitchFromCloud;
import com.kenan.socket.netty.NettyClientHandler;
import com.kenan.socket.netty.NettyClientPipelineFactory;
import com.kenan.socket.netty.NettyProtocolUtil;
import com.kenan.socket.netty.NettyRequest;
import com.kenan.socket.netty.NettyRequestFactory;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class NettyClient {
    public static final String TAG="NettyClient";
    public static final String HOST ="182.61.53.107";
    public static final int PORT =8001;
    public static long hear_beat_time =10*1000;

    private boolean isConnect = false;
    private ChannelFuture future;
    private Channel channel;

    private  static NioClientSocketChannelFactory clientSocketChannelFactory;
    private  ClientBootstrap clientBootstrap;
    private  ExecutorService singleThreadExecutor=Executors.newSingleThreadExecutor();

    private String ACTION_BROADCAST = "com.baidu.lbs.xinlingshou.netty";
    private TimerSchedule mRingTimerSchedule;

    private static NettyClient nettyClient =new NettyClient();

    private NettyClient(){
        mRingTimerSchedule = new TimerSchedule(DuApp.getAppContext(), ACTION_BROADCAST, mTimerScheduleCallback);
    }

    public static NettyClient getInstance(){
        return nettyClient;
    }

    public void checkStatus(SwitchFromCloud.Switches switches){
        try {
            if (switches == null || switches.pconnect_switch == null) {
                return;
            }
            boolean canopen = "1".equals(switches.pconnect_switch.open);
            int heartBeatTime = Integer.parseInt(switches.pconnect_switch.period);
            this.hear_beat_time = heartBeatTime*1000;

            Log.i(TAG,"isConnect:"+isConnect()+"****isOpen:"+canopen);
            if (isConnect()) {
                if (!canopen) {
                    this.close();
                }
            } else {
                if (canopen) {
                    this.start();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){
        Log.i(TAG,"run-"+ "***********start**********");


        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"run-"+ System.currentTimeMillis());
                startProcess();
            }
        };

        singleThreadExecutor.execute(runnable);
    }

    public void close(){
        Log.i(TAG,"run-"+ "************close**********");
        try {
            shutdown(future);
            stopHeartBeat();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void startProcess(){
        try {
            shutdown(future);
            Thread.sleep(500);
            clientSocketChannelFactory=new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool());
            clientBootstrap=new ClientBootstrap(clientSocketChannelFactory);

            ChannelFuture future = connect();
            Log.i(TAG,"future state is "+future.isSuccess());

            channel=future.getChannel();
            if(future.isSuccess()){
                sendAuth();
            }
        }catch (Exception e){
            e.printStackTrace();
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
//        if(channel==null){
//            return false;
//        }
//        return  channel.isConnected();
        return false;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }



    public void sendHeartBeat(){

            NettyRequest nettyRequest = NettyRequestFactory.getHeartBeatRequest();
            ChannelBuffer channelBuffer = NettyProtocolUtil.encodeRequest(NettyRequestFactory.ENCODE.UTF_8, nettyRequest);
            Log.i(NettyClient.TAG, nettyRequest.toString());
            sendMsgToServer(channelBuffer, new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {

                    } else {

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
                    startHeartBeat();
                }else{
                }
            }
        });
    }

    public void sendNewOrderResponse(){
        NettyRequest nettyRequest=NettyRequestFactory.getOrderRequest();
        ChannelBuffer channelBuffer=NettyProtocolUtil.encodeRequest(NettyRequestFactory.ENCODE.UTF_8, nettyRequest);
        Log.i(NettyClient.TAG,nettyRequest.toString());
        sendMsgToServer(channelBuffer, new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){

                }else{
                }
            }
        });
    }



    private NettyClientHandler.NettyHandlerListener nettyHandlerListener=new NettyClientHandler.NettyHandlerListener() {
        @Override
        public void onSuccess(ChannelHandlerContext ctx, MessageEvent e)
        {
        }

        @Override
        public void onError(ChannelHandlerContext ctx, ExceptionEvent e) {

        }

        @Override
        public void onClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
            setConnect(false);
        }
    };


    public void stopHeartBeat() {
        mRingTimerSchedule.stop();
    }

    private void startHeartBeat() {
        Log.i(TAG,"start--HB-TIMER---"+hear_beat_time);
        mRingTimerSchedule.start(5*1000, hear_beat_time, hear_beat_time, hear_beat_time);
    }

    private TimerSchedule.TimerScheduleCallback mTimerScheduleCallback = new TimerSchedule.TimerScheduleCallback() {
        @Override
        public void doSchedule() {
            Log.i(TAG,"send-HB------------------"+hear_beat_time);
            sendHeartBeat();
            sendNewOrderResponse();
        }
    };





}
