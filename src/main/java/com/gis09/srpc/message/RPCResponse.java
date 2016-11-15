package com.gis09.srpc.message;

import java.io.Serializable;

/**
 * Created by huyoufu on 2016/9/27.
 */
public class RPCResponse implements Serializable {
    private static final long serialVersionUID = -4766694280335410140L;
    private String requestId;
    private Throwable throwable;
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public RPCResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public RPCResponse setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RPCResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "RPCResponse{" +
                "requestId='" + requestId + '\'' +
                ", throwable=" + throwable +
                ", result=" + result +
                '}';
    }
}
