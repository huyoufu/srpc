package com.gis09.srpc.message;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by huyoufu on 2016/9/27.
 * rpc 请求
 */
public class RPCRequest implements Serializable{
    private static final long serialVersionUID = -6304346628645907957L;
    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public RPCRequest setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public RPCRequest setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RPCRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public RPCRequest setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public RPCRequest setParameters(Object[] parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public String toString() {
        return "RPCRequest{" +
                "requestId='" + requestId + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
