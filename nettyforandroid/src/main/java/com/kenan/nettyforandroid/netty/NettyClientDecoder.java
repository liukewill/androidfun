package com.kenan.nettyforandroid.netty;

import com.kenan.nettyforandroid.protocol.NettyProtocolUtil;
import com.kenan.nettyforandroid.protocol.NettyResponse;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Created by kenan on 17/11/6.
 * NettyResponse解析器
 * 可自定义，参照本解析器实现
 */

public class NettyClientDecoder extends FrameDecoder {
    @Override
    protected Object decode(ChannelHandlerContext context, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes()<16) {
            return null;
        }
        buffer.markReaderIndex();
        byte[] bytes=buffer.array();

        byte encode=buffer.readByte();
        byte encrypt=buffer.readByte();
        byte extend1=buffer.readByte();
        byte extend2=buffer.readByte();
        int sessionid=buffer.readInt();
        int result=buffer.readInt();
        int length=buffer.readInt(); // 数据包长
        if (buffer.readableBytes()<length) {
            buffer.resetReaderIndex();
            return null;
        }
        ChannelBuffer dataBuffer= ChannelBuffers.buffer(length);
        buffer.readBytes(dataBuffer, length);

        NettyResponse response=new NettyResponse();
        response.setEncode(encode);
        response.setEncrypt(encrypt);
        response.setExtend1(extend1);
        response.setExtend2(extend2);
        response.setSessionid(sessionid);
        response.setResult(result);
        response.setLength(length);
        response.setValues(NettyProtocolUtil.decodePack(encode, dataBuffer));
        response.setIp(NettyProtocolUtil.getClientIp(channel));
        return response;
    }
}
