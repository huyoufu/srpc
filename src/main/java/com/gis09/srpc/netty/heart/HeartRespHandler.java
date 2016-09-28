package com.gis09.srpc.netty.heart;

import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/9/28.
 */
public class HeartRespHandler extends ChannelHandlerAdapter {
    private Logger log = LoggerFactory.getLogger(getClass());

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        if ((message.getHeader() != null) && (message.getHeader().getType() == Header.TYPE_HEART_REQ)) {
            if (this.log.isInfoEnabled()) {
                this.log.info("客户端 sessionId 是" + message.getHeader().getSessionId() + " 来心跳了");
            }
            ctx.writeAndFlush(buildHeartResp(message.getHeader().getSessionId()));
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private Message buildHeartResp(long sessionId) {
        Message message = new Message();
        Header header = new Header();
        header.setType(Header.TYPE_HEART_RESP);
        header.setSessionId(sessionId);
        message.setHeader(header);
        return message;
    }


}
