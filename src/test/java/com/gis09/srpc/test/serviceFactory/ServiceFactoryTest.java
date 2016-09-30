package com.gis09.srpc.test.serviceFactory;

import com.gis09.srpc.service.AsynService;
import com.gis09.srpc.service.AsynServiceListener;
import com.gis09.srpc.service.DefaultAsynServiceListener;
import com.gis09.srpc.serviceFactory.ProxyServiceFactory;
import com.gis09.srpc.serviceFactory.ServiceFactory;
import io.netty.util.concurrent.CompleteFuture;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2016/9/29.
 */
public class ServiceFactoryTest {
    @Test
    public void testProxy(){
        ServiceFactory serviceFactory=new ProxyServiceFactory();
        final HelloService build = serviceFactory.build(HelloService.class);
        final String s = build.hello("测试");
        System.out.println(s);
    }
    @Test
    public void testAsyn(){
        ServiceFactory serviceFactory=new ProxyServiceFactory();
        final AsynService asynService = serviceFactory.buildAsynService(HelloService.class);
        asynService.AddListener(new DefaultAsynServiceListener<>());
        asynService.excute("hello",new Object[]{"小明"});
    }
    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture=CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "11";
        });
        String s = null;
        try {
            s = completableFuture.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }



    private interface HelloService{
        String hello(String name);
    }

}
