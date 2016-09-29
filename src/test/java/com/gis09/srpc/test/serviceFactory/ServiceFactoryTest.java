package com.gis09.srpc.test.serviceFactory;

import com.gis09.srpc.service.AsynService;
import com.gis09.srpc.service.AsynServiceListener;
import com.gis09.srpc.service.DefaultAsynServiceListener;
import com.gis09.srpc.serviceFactory.ProxyServiceFactory;
import com.gis09.srpc.serviceFactory.ServiceFactory;
import org.junit.Test;

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




    private interface HelloService{
        String hello(String name);
    }

}
