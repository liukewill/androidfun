package com.kenan.okhttp_client.net.dns;

import android.content.Context;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Dns;

/**
 * Created by iwm on 2016/5/31.
 */
public class WMDNS implements Dns {

    private Context mContext;
    private long mCacheTime;

    public WMDNS(Context context, long cacheTime) {
        this.mContext = context;
        this.mCacheTime = cacheTime;
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        List<InetAddress> result=new ArrayList<InetAddress>();
        List<String> ipResult = HttpDNSUtil.getIPByHost(mContext, hostname, mCacheTime);
        if (ipResult != null) {
            InetAddress addr = null;
            for (int i = 0; i < ipResult.size(); i++) {
                try {
                    String[] temp = ipResult.get(i).split("\\.");
                    byte[] byteArray = new byte[]{
                            (byte) Integer.parseInt(temp[0]),
                            (byte) Integer.parseInt(temp[1]),
                            (byte) Integer.parseInt(temp[2]),
                            (byte) Integer.parseInt(temp[3])};
                    addr = InetAddress.getByAddress(byteArray);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                result.add(addr);
            }
        }
        try {
            result.addAll(Dns.SYSTEM.lookup(hostname));
        } catch (Exception e){
            //捕获网络权限异常
            Log.w("WMDNS",e.getMessage());
        }
        return result;
    }
}
