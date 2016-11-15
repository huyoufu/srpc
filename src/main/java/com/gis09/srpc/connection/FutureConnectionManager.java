package com.gis09.srpc.connection;

import com.gis09.srpc.message.RPCRequest;
import com.gis09.srpc.message.RPCRequestFuture;
import com.gis09.srpc.message.RPCResponse;
import com.gis09.srpc.message.RPCResponseFuture;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/8.
 */
public class FutureConnectionManager implements ConnectionManager{
    private Logger log= LoggerFactory.getLogger(getClass());
    private volatile float threshold=0.8f;
    private volatile int MAX_SIZE=2048;
    private Map<RPCRequestFuture,RPCResponseFuture> taskContainer= Maps.newConcurrentMap();
    @Override
    public void service(RPCRequest request, RPCResponse response) {
        _service((RPCRequestFuture)request,null);
    }
    public void _service(RPCRequestFuture requestFuture,RPCResponseFuture responseFuture){

    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
    public void checkSize(){}
}
