package com.gis09.srpc.netty.codec;

import com.gis09.srpc.message.RPCRequest;
import com.gis09.srpc.netty.message.BodyWrapper;
import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;
import com.gis09.srpc.utils.SerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyoufu on 2016/9/28.
 * 消息的decoder
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,int lengthAdjustment) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,lengthAdjustment,0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = (ByteBuf) super.decode(ctx, in);
        if (buf==null) return null;
        Message message=new Message();
        Header header=new Header();
        header.setVersion(buf.readInt());
        header.setLength(buf.readInt());
        header.setSessionId(buf.readLong());
        header.setType(buf.readByte());
        header.setPriority(buf.readByte());
        int size=buf.readInt();//attachment的大小
        if (size>0) {
            Map<String, String> attachment = new HashMap<String, String>(size);
            int key_size = 0;
            byte[] key_array = null;
            String key = null;
            for (int i = 0; i < size; i++) { //开始遍历map组装
                key_size = buf.readInt();
                key_array = new byte[key_size];
                buf.readBytes(key_array);
                key = new String(key_array, "utf-8");
                attachment.put(key,attachmentDecode(buf));
            }
            key_array = null;
            key = null;
            header.setAttachment(attachment);
        }
        if (buf.readableBytes()>4) {
            try {
                message.setBodyWrapper((BodyWrapper) objectDecode(buf));
            }catch (Exception e){
                System.out.println("解析body的时候出错了");
                e.printStackTrace();
            }
        }
        message.setHeader(header);
        return message;
    }
    private String attachmentDecode(ByteBuf buf){
        return objectDecode(buf,String.class);
    }
    private <T> T objectDecode(ByteBuf buf,Class<T> targetClass){
        int object_size=buf.readInt(); //对象大小
        final byte[] swap=new byte[object_size];
        buf.readBytes(swap);
        T deserialize = SerializerUtil.deserialize(swap, targetClass);
        return deserialize;
    }
    private Object objectDecode(ByteBuf buf) throws UnsupportedEncodingException, ClassNotFoundException {
        //普通的对象转换就要获取其类名用反射来做
        int classNameLength = buf.readInt();//获取类名的长度
        byte[] classNameArray=new byte[classNameLength];
        buf.readBytes(classNameArray);
        String className = SerializerUtil.deserialize(classNameArray, String.class); //注意这里必须是反序列话出来的 不能使用构造字符串的方式 否则会导致无法识别类名
        Class<?> aClass = Class.forName(className);
        return objectDecode(buf,aClass);
    }
}
