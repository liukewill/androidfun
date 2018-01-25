package com.kenan.socket.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * Created by kenan on 17/11/6.
 */

public class NettyClientPipelineFactory implements ChannelPipelineFactory {
    public static final String HANDLER="handler";
    public static final String DECODER="decoder";
    NettyClientHandler.NettyHandlerListener nettyHandlerListener=null;

    public NettyClientPipelineFactory(NettyClientHandler.NettyHandlerListener handlerListener){
        this.nettyHandlerListener=handlerListener;
    }
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline= Channels.pipeline();
        /**
         * 使用专用的解码器，解决数据分段的问题
         * 从业务逻辑代码中分离协议处理部分总是一个很不错的想法。
         */
        pipeline.addLast(DECODER, new NettyClientDecoder());
        /**
         * 有专门的编码解码器，这时处理器就不需要管数据分段和数据格式问题，只需要关注业务逻辑了！
         */
        NettyClientHandler nettyClientHandler=new NettyClientHandler();
        nettyClientHandler.addListener(nettyHandlerListener);
        pipeline.addLast(HANDLER, nettyClientHandler);
        return pipeline;
    }

}
