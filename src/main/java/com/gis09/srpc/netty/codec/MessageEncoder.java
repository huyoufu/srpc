package com.gis09.srpc.netty.codec;

import com.gis09.srpc.message.RPCRequest;
import com.gis09.srpc.netty.message.Message;
import com.gis09.srpc.utils.SerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Map;

/**
 * Created by huyoufu on 2016/9/28.
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {
    private final byte[] EMPTY_ARRAY=new byte[4];//这里我们用4个字节来表示一个对象的大小 4个字节就是一个int的可表示的最大存储空间

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        if (msg==null||msg.getHeader()==null) {
            throw new RuntimeException("the msg is null");
        }
        ByteBuf buf=out;
        buf.writeInt(msg.getHeader().getVersion());
        buf.writeInt(msg.getHeader().getLength());
        buf.writeLong(msg.getHeader().getSessionId());
        buf.writeByte(msg.getHeader().getType());
        buf.writeByte(msg.getHeader().getPriority());
        buf.writeInt(msg.getHeader().getAttachment().size());
        String key=null;
        byte[] key_array=null;
        Object value=null;
        for (Map.Entry<String, String> entry : msg.getHeader().getAttachment().entrySet()) {
            key=entry.getKey();
            key_array=key.getBytes("utf-8");
            buf.writeInt(key_array.length);
            buf.writeBytes(key_array);
            value=entry.getValue();
            objectEncode(value, buf);
        }
        key=null;
        key_array=null;
        value=null;
        if (msg.getBodyWrapper()!=null) {
            objectEncode(msg.getBodyWrapper(), buf);
        }else{
            buf.writeInt(0);
        }
        buf.setInt(4, buf.readableBytes()); //这里是因为length的位置是4 所以 其实将整个message消息体的大小写进去了
    }
    private void attachmentEncode(String s,ByteBuf out){
        _objectEncode(s, out);
    }

    private void objectEncode(Object obj,ByteBuf out){
        //获取obj的类名
        String className = obj.getClass().getName();
        System.out.println(className);
        _objectEncode(className,out);
        _objectEncode(obj,out);
    }
    

    private void _objectEncode(Object obj,ByteBuf out){
        int length_pos=out.writerIndex();//获取当前buf的写位置的索引
        out.writeBytes(EMPTY_ARRAY);
        byte[] serialize = SerializerUtil.serialize(obj);
        out.writeBytes(serialize);//将对象的字节数组写进去
        //out.setInt(length_pos, out.writerIndex()-length_pos-4); //out.writerIndex()-length_pos-4 可以获取到写入到buf中的字节数组的大小
        out.setInt(length_pos, serialize.length); //将对象大小写会到原来的站位符上 其实跟上面的写法是一个作用
    }
}
