package com.gis09.srpc.netty.business;

import com.gis09.srpc.netty.message.BodyWrapper;
import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/9/28.
 */
public class BusinessHandler extends ChannelHandlerAdapter {
    private Logger log= LoggerFactory.getLogger(getClass());
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof Message) {
            Message message=(Message) msg;
            if (message.getHeader()!=null&&message.getHeader().getType()== Header.TYPE_BI_2R) {
                //业务消息请求
                BodyWrapper bodyWrapper = message.getBodyWrapper();
                System.out.println(message.getBodyWrapper());
            }

        }else{
            ctx.fireChannelRead(msg);
        }
    }
}
