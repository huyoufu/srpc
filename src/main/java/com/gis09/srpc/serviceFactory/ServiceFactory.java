package com.gis09.srpc.serviceFactory;

import com.gis09.srpc.service.AsynService;

/**
 * Created by Administrator on 2016/9/29.
 */
public interface ServiceFactory {
    default <T> T build(Class<T> serviceClass){
        return build(true,serviceClass);
    }
    <T> T build(boolean single,Class<T> serviceClass);

    default AsynService buildAsynService(Class serviceClass){
       return buildAsynService(true,serviceClass);
    }
    AsynService buildAsynService(boolean single,Class serviceClass);

}
