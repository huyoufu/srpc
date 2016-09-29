package com.gis09.srpc.netty.client;

import com.gis09.srpc.message.RPCRequest;
import com.gis09.srpc.netty.ack.LoginHandler;
import com.gis09.srpc.netty.business.BusinessHandler;
import com.gis09.srpc.netty.codec.MessageDecoder;
import com.gis09.srpc.netty.codec.MessageEncoder;
import com.gis09.srpc.netty.heart.HeartReqHandler;
import com.gis09.srpc.netty.message.BodyWrapper;
import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;
import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Client {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private Bootstrap bootstrap = new Bootstrap();// 创建一个启动器
    private EventLoopGroup group = new NioEventLoopGroup();
    private ClientConfig clientConfig;
    private ConcurrentHashMap<String, Channel> channelTables = new ConcurrentHashMap<String, Channel>();
    private Channel channel;
    /**
     * 构造函数 必须传入一个clientConfig
     * @param clientConfig
     */
    public Client(ClientConfig clientConfig) {
        super();
        this.clientConfig = clientConfig;
    }
    public void init(){
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch)
                            throws Exception {
                        ch.pipeline().addLast(
                                new MessageDecoder(1024 * 1024, 4, 4,-8));
                        ch.pipeline().addLast("messageEncoder",
                                new MessageEncoder());
                        ch.pipeline().addLast("readTimeoutHandler",
                                new ReadTimeoutHandler(clientConfig.getReadTimeOut()));
                        ch.pipeline().addLast("loginHandler",
                                new LoginHandler());//登录处理
                        ch.pipeline().addLast("heartReqHandler",
                                new HeartReqHandler()); //处理心跳
                        //ch.pipeline().addLast("businessRespHandler",new BusinessHandler());
                    }
                });
    }

    public void connect() {
        try {
            ChannelFuture future = bootstrap.connect(clientConfig.getServerHost(), clientConfig.getServerPort()).sync();
            channel = future.channel();
            //this.channelTables.put("srpc", future.channel());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("client occur exception {}", e.getCause());
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(clientConfig.getReconnectInterval());
                        connect();// 发起重连
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    public void send(Message message){
        NioSocketChannel nioSocketChannel= (NioSocketChannel) this.channel;
        nioSocketChannel.writeAndFlush(message);
    }
    public static void main(String args[]) throws InterruptedException {

        ClientConfig config=new ClientConfig("xiaoming","123");
        Client client=new Client(config);
        client.init();
        client.connect();
        Thread.sleep(3000);
        System.out.println("发送信息1");
        Message message=new Message();
        Header header=new Header();
        header.setType(Header.TYPE_BI_2R);
        message.setHeader(header);
        RPCRequest request=new RPCRequest();
        request.setClassName("测试雷鸣");
        Map<String,Object> params= Maps.newHashMap();
        params.put("key",request);
        message.setBodyWrapper(new BodyWrapper(params));
        System.out.println("发送信息");
        client.send(message);
    }
}
