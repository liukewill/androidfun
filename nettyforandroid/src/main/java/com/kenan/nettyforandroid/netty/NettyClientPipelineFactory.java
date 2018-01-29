package com.kenan.nettyforandroid.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Created by kenan on 17/11/6.
 */

public class NettyClientPipelineFactory implements ChannelPipelineFactory {
    public static final String HANDLER="handler";
    public static final String DECODER="nettyDecoder";
    INettyHandler nettyHandlerListener=null;
    FrameDecoder frameDecoder;

    public NettyClientPipelineFactory(FrameDecoder frameDecoder, INettyHandler handlerListener){
        this.nettyHandlerListener=handlerListener;
        this.frameDecoder=frameDecoder;
    }
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline= Channels.pipeline();
        /**
         * 使用专用的解码器，解决数据分段的问题
         */
        pipeline.addLast(DECODER, frameDecoder);
        /**
         * 有专门的编码解码器，这时处理器就不需要管数据分段和数据格式问题，只需要关注业务逻辑
         */
        NettyClientHandler nettyClientHandler=new NettyClientHandler();
        nettyClientHandler.addListener(nettyHandlerListener);
        pipeline.addLast(HANDLER, nettyClientHandler);
        return pipeline;
    }

}
