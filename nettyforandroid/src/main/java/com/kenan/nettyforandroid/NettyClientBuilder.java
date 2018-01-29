package com.kenan.nettyforandroid;

import android.content.Context;

/**
 * Created by kenan on 18/1/25.
 */

public class NettyClientBuilder {

    public   String HOST ;
    public   int PORT ;
    public  long hear_beat_time =10*1000;
    public  String ACTION_BROADCAST = "com.kenan.nettyforandroid.action";
    public Context context;

    public NettyClientBuilder(){

    }

    public NettyClientBuilder setHOST(String HOST) {
        this.HOST = HOST;
        return this;
    }

    public NettyClientBuilder setPORT(int PORT) {
        this.PORT = PORT;
        return this;
    }

    public NettyClientBuilder setHear_beat_time(long hear_beat_time) {
        this.hear_beat_time = hear_beat_time;
        return this;
    }

    public NettyClientBuilder setACTION_BROADCAST(String ACTION_BROADCAST) {
        this.ACTION_BROADCAST = ACTION_BROADCAST;
        return this;
    }

    public NettyClientBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public void build(){
        NettyClient.getInstance().setHOST(this.HOST);
        NettyClient.getInstance().setPORT(this.PORT);
        NettyClient.getInstance().setHear_beat_time(this.hear_beat_time);
        NettyClient.getInstance().setContext(this.context);
    }
}
