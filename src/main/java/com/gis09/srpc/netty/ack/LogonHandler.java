package com.gis09.srpc.netty.ack;

import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/9/28.
 */
public class LogonHandler extends ChannelHandlerAdapter {
    private Logger log = LoggerFactory.getLogger(getClass());

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        super.exceptionCaught(ctx, cause);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        Message message = (Message)msg;
        if ((message.getHeader() != null) && (message.getHeader().getType() == Header.TYPE_ACK_REQ)) {
            if (log.isInfoEnabled()) {
                log.info("有客户端来登录了...");
                log.info("客户端:username is {},密码是 ******",message.getHeader().getAttachment().get(""));
            }
            ctx.writeAndFlush(buildLogon());
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private Message buildLogon() { Message message = new Message();
        Header header = new Header();
        header.setType(Header.TYPE_ACK_RESP);
        //header.setSessionId(IdGenerator.timeRand());
        message.setHeader(header);
        return message;
    }


}
