package com.gis09.srpc.netty.message;

/**
 * Created by Administrator on 2016/9/29.
 * 修正 Protostuff bug导致的不能序列化map list集合 要包裹起来才可以
 */
public class BodyWrapper {
    public BodyWrapper() {
    }

    public BodyWrapper(Object object) {
        this.object = object;
    }

    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
