package com.gis09.srpc.service;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2016/9/29.
 */
public interface AsynService {
    /**
     * 添加异步服务方法的监听器
     */
    void AddListener(AsynServiceListener<?> asynServiceListener);

    void removeListener(AsynServiceListener<?> asynServiceListener);

    <V>FutureTask<V> excute(String methodName,Object[] args);
}
