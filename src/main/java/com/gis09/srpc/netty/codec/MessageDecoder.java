package com.gis09.srpc.netty.codec;

import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;
import com.gis09.srpc.utils.SerializerUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huyoufu on 2016/9/28.
 * 消息的decoder
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
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
            Map<String, Object> attachment = new HashMap<String, Object>(size);
            int key_size = 0;
            byte[] key_array = null;
            String key = null;
            for (int i = 0; i < size; i++) { //开始遍历map组装
                key_size = buf.readInt();
                key_array = new byte[key_size];
                buf.readBytes(key_array);
                key = new String(key_array, "utf-8");
                attachment.put(key, objectDecode(buf));
            }
            key_array = null;
            key = null;
            header.setAttachment(attachment);
        }
        if (buf.readableBytes()>4) {
            message.setBody(objectDecode(buf));
        }
        message.setHeader(header);
        return message;
    }
    private Object objectDecode(ByteBuf buf){
        int object_size=buf.readInt(); //对象大小
        ByteBuf slice = buf.slice(buf.readerIndex(), object_size);
        Object deserialize = SerializerUtil.deserialize(slice.array(), Object.class);
        return deserialize;
    }
}
