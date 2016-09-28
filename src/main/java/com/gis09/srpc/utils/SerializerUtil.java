package com.gis09.srpc.utils;

import com.gis09.srpc.config.RPCConfig;
import com.gis09.srpc.message.RPCRequest;
import com.gis09.srpc.netty.message.BodyWrapper;
import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;
import com.google.common.collect.Maps;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/28.
 */
public class SerializerUtil {
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new RuntimeException("序列化对象(" + obj + ")!");
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj
                    + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    public static <T> T deserialize(byte[] paramArrayOfByte,
                                    Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        T instance = null;
        try {
            instance = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e);
        }
        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
        return instance;
    }
    public static void main(String[] args) throws ClassNotFoundException {
        Message message=new Message();
        Header header=new Header();
        header.setType(Header.TYPE_BI_2R);
        message.setHeader(header);
        RPCRequest request=new RPCRequest();
        request.setClassName("测试雷鸣");
        Map<String,Object> params= Maps.newHashMap();
        params.put("key", request);
        message.setBodyWrapper(new BodyWrapper(params));
        byte[] serialize = serialize(message);
        Object deserialize = deserialize(serialize,Message.class);
        System.out.println(deserialize);
    }

}
