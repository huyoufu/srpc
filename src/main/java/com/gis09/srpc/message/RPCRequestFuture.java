package com.gis09.srpc.message;

/**
 * Created by Administrator on 2016/10/8.
 */
public class RPCRequestFuture extends RPCRequest {
    private long expireTime;
    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
