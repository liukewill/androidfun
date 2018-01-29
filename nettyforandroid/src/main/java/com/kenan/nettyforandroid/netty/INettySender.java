package com.kenan.nettyforandroid.netty;

/**
 * Created by kenan on 18/1/25.
 */

public interface INettySender {
    /**
     * 验证 connect成功后进行验证
     * 建议放在INettyHandlerListener.onConnectSuccess 回调中执行
     */
    void sendAuth();

    /**
     * 发送心跳
     */
    void sendHeartBeat();
}
