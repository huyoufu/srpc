package com.gis09.srpc.client;

import com.gis09.srpc.message.RPCRequest;
import com.gis09.srpc.netty.client.Client;
import com.gis09.srpc.netty.client.ClientConfig;
import com.gis09.srpc.netty.message.BodyWrapper;
import com.gis09.srpc.netty.message.Header;
import com.gis09.srpc.netty.message.Message;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * Created by huyoufu on 2016/9/27.
 */
public class RPCClient {
    private Client client;
    private ClientConfig clientConfig;
    private volatile boolean isInited=false;
    private CountDownLatch countDownLatch=new CountDownLatch(1);
    public void init(){
        client.init();
        Thread thread = new Thread(() -> {
            client.connect();
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.State state = thread.getState();
        isInited=true;
    }
    public RPCClient (String serverHost){
        clientConfig=new ClientConfig(serverHost);
        client=new Client(clientConfig);
    }
    public RPCClient (){
        clientConfig=new ClientConfig("127.0.0.1");
        client=new Client(clientConfig);
    }

    public void send(RPCRequest request){
        if (!isInited){
            init();
        }
        client.send(buildMessage(request));
    }
    private Message buildMessage(RPCRequest request){
        Message message=new Message();
        Header header=new Header();
        header.setType(Header.TYPE_BI_2R);
        message.setHeader(header);
        message.setBodyWrapper(new BodyWrapper(request));
        return message;
    }
    public static void main(String[] args){
        RPCClient rpcClient=new RPCClient();
        for (int i=0;i<10;i++){
            RPCRequest request=new RPCRequest();
            request.setMethodName(String.valueOf(i));
            rpcClient.send(request);
        }
    }

}
