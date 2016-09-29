package com.gis09.srpc.serviceFactory;

import com.gis09.srpc.service.AsynService;
import com.gis09.srpc.service.DefaultAsynService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2016/9/29.
 */
public class ProxyServiceFactory implements ServiceFactory {
    @Override
    public <T> T build(boolean single, Class<T> serviceClass) {
        T t =(T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                final String className = method.getDeclaringClass().getName();
                for (Object arg : args) {

                }
                return "测试代理类"+args[0];
            }
        });
        return t;
    }

    @Override
    public AsynService buildAsynService(boolean single, Class serviceClass) {
        final Object build = build(single, serviceClass);
        DefaultAsynService result=new DefaultAsynService(build);
        return result;
    }
}
