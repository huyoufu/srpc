package com.gis09.srpc.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/9/29.
 */
public class DefaultAsynServiceListener<T> implements AsynServiceListener<T> {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public void onCompleted(T t) {
        logger.debug("onCompleted-->{}",t);
    }

    @Override
    public void onFailed(Throwable throwable) {
        logger.error("failed",throwable);
    }
}
