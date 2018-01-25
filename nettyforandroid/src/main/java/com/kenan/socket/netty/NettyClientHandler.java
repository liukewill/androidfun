package com.kenan.socket.netty;

import android.util.Log;

import com.baidu.lbs.manager.NoticeManager;
import com.kenan.socket.NettyClient;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by kenan on 17/11/6.
 */

public class NettyClientHandler extends SimpleChannelHandler {
    private final AtomicInteger count=new AtomicInteger(0); //计数器
    private List<NettyHandlerListener> listeners =new ArrayList<>();

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        notifySuccess(ctx,e);
        processMethod1(ctx, e); //处理方式一
        Log.i(NettyClient.TAG,e.toString());
    }

    /**
     * @param ctx
     * @param e
     * @throws Exception
     */
    public void processMethod1(ChannelHandlerContext ctx, MessageEvent e) throws Exception{
        Log.i(NettyClient.TAG,e.getMessage().toString());

        NettyResponse response=(NettyResponse)e.getMessage();


        if(NettyRequestFactory.COMMOND.AUTH==response.getResult()){
            NettyRequestFactory.setTicket(response.getValue(NettyRequestFactory.CONSTANT.TICKET_KEY));
            Log.i(NettyClient.TAG,"AUTH-RESPONSE");

        }

        if(NettyRequestFactory.COMMOND.HEART_BEAT==response.getResult()){
            Log.i(NettyClient.TAG,"HB-RESPONSE");
        }

        if(NettyRequestFactory.COMMOND.NEW_ORDER==response.getResult()){
            //新单逻辑  收到新单推送，反馈新单，走notice逻辑
            Log.i(NettyClient.TAG,"NEW-ORDER-RESPONSE");
            NettyClient.getInstance().sendNewOrderResponse();
            NoticeManager.getInstance().notice();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        notifyError(ctx,e);
        e.getCause().printStackTrace();
        ctx.getChannel().close();
        Log.i(NettyClient.TAG,"channelClosed--exception"+e.toString());

    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelClosed(ctx, e);
        notifyClosed(ctx,e);
        Log.i(NettyClient.TAG,"channelClosed"+e.toString());
    }


    public void addListener(NettyHandlerListener listener){
        if(listener!=null&&!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public void removeListener(NettyHandlerListener listener){
        if(listener!=null&& listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    private void notifySuccess(ChannelHandlerContext ctx, MessageEvent e){
        for (NettyHandlerListener nettyHandlerListener : listeners) {
            nettyHandlerListener.onSuccess(ctx,e);
        }
    }

    private void notifyError(ChannelHandlerContext ctx, ExceptionEvent e){
        for (NettyHandlerListener nettyHandlerListener : listeners) {
            nettyHandlerListener.onError(ctx,e);
        }
    }

    private void notifyClosed(ChannelHandlerContext ctx, ChannelStateEvent e){
        for (NettyHandlerListener nettyHandlerListener : listeners) {
            nettyHandlerListener.onClosed(ctx,e);
        }
    }

    public interface NettyHandlerListener{
        void onSuccess(ChannelHandlerContext ctx, MessageEvent e);
        void onError(ChannelHandlerContext ctx, ExceptionEvent e);
        void onClosed(ChannelHandlerContext ctx, ChannelStateEvent e);
    }
}
