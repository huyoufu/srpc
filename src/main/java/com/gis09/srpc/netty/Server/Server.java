package com.gis09.srpc.netty.Server;

import com.gis09.srpc.netty.ack.LogonHandler;
import com.gis09.srpc.netty.business.BusinessHandler;
import com.gis09.srpc.netty.codec.MessageDecoder;
import com.gis09.srpc.netty.codec.MessageEncoder;
import com.gis09.srpc.netty.heart.HeartRespHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Server {
    public void bind(int port){
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup slavers=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boss,slavers).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MessageDecoder(1024 * 1024, 4, 4, -8));
                            ch.pipeline().addLast("messageEncoder", new MessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(20));
                            ch.pipeline().addLast("logonHandler", new LogonHandler());
                            ch.pipeline().addLast("heartReqHandler", new HeartRespHandler());
                            ch.pipeline().addLast("businessHandler", new BusinessHandler());
                        }
                    });
            ChannelFuture bind = bootstrap.bind(port);
            System.out.println("启动成功");
            bind.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            boss.shutdownGracefully();
            slavers.shutdownGracefully();
        }
    }
    public static void main(String args[]){
        Server server=new Server();
        server.bind(52018);
    }
}
