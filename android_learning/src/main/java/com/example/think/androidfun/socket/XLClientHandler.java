package com.example.think.androidfun.socket;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by kenan on 17/11/6.
 */

public class XLClientHandler extends SimpleChannelHandler {
    private final AtomicInteger count=new AtomicInteger(0); //计数器

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        processMethod1(ctx, e); //处理方式一
    }

    /**
     * @param ctx
     * @param e
     * @throws Exception
     */
    public void processMethod1(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        XLResponse serverTime=(XLResponse)e.getMessage();
        Thread.sleep(1000);

        if (count.get()<10) {
            //从新发送请求获取最新的服务器时间
            ctx.getChannel().write(ChannelBuffers.wrappedBuffer("again\r\n".getBytes()));
        }else{
            //从新发送请求关闭服务器
//            ctx.getChannel().write(ChannelBuffers.wrappedBuffer("shutdown\r\n".getBytes()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        ctx.getChannel().close();
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelClosed(ctx, e);
    }
}
