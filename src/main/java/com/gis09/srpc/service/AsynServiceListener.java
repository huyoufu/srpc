package com.gis09.srpc.service;

/**
 * Created by Administrator on 2016/9/29.
 */
public interface AsynServiceListener<T> {
    void onCompleted(T t);
    void onFailed(Throwable throwable);
}
