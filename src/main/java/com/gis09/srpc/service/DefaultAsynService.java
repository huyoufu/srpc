package com.gis09.srpc.service;

import com.google.common.collect.Lists;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2016/9/29.
 */
public class DefaultAsynService implements AsynService {
    private final Object oriService;
    public DefaultAsynService(Object oriService){
        this.oriService=oriService;
    }
    private List<AsynServiceListener> asynServiceListeners= Lists.newArrayList();
    @Override
    public synchronized void AddListener(AsynServiceListener<?> asynServiceListener) {
        asynServiceListeners.add(asynServiceListener);
    }

    @Override
    public synchronized void removeListener(AsynServiceListener<?> asynServiceListener) {
        asynServiceListeners.remove(asynServiceListener);
    }

    @Override
    public final <V> FutureTask<V> excute(String methodName,final Object[] args) {
        final Method[] declaredMethods = oriService.getClass().getDeclaredMethods();
        for (Method  method:declaredMethods){
            final String name = method.getName();
            if (!name.equals(methodName)) continue;
            try {
                method.setAccessible(true);
                final Object invoke = method.invoke(oriService, args);
                for (AsynServiceListener l:asynServiceListeners){
                    l.onCompleted(invoke);
                }
            }catch (Exception e) {
                e.printStackTrace();
                for (AsynServiceListener l:asynServiceListeners){
                    l.onFailed(e);
                }
            }
        }
        return null;
    }

}
