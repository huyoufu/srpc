package com.gis09.srpc.connection;

import com.gis09.srpc.message.RPCRequest;
import com.gis09.srpc.message.RPCResponse;

/**
 * Created by Administrator on 2016/10/8.
 */
public interface ConnectionManager {
    void service(RPCRequest request,RPCResponse response);
}
