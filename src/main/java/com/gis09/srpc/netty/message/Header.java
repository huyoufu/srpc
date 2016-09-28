package com.gis09.srpc.netty.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huyoufu on 2016/9/28.
 */
public class Header {
    public static final Byte TYPE_BI_REQ=0x0; //业务请求消息
    public static final Byte TYPE_BI_RESP=0x1; //业务应答消息
    public static final Byte TYPE_BI_2R=0x2; //双通道业务消息
    public static final Byte TYPE_ACK_REQ=0x3; //ack_req 握手请求 消息
    public static final Byte TYPE_ACK_RESP=0x4; //ack_resp 握手应答消息
    public static final Byte TYPE_HEART_REQ=0x5; //heart_req 心跳请求消息
    public static final Byte TYPE_HEART_RESP=0x6; //heart_resp 心跳应答消息
    public static final Byte TYPE_CLOSE_REQ=0X7;//链路关闭请求消息
    public static final byte TYPE_CLOSE_RESP=0x8;//链路关闭回应消息
    public static final byte TYPE_PRIORITY_DEFAULT=0x17;
    private int version=0x20160101;//版本号
    private int length; //消息长度
    private long sessionId;//sessionId  这里其实应该指的是客户端id
    private Byte type; //消息类型
    private Byte priority=TYPE_PRIORITY_DEFAULT; //消息优先级别
    private Map<String, String> attachment=new HashMap<String, String>(); //附件

    public int getVersion() {
        return version;
    }

    public Header setVersion(int version) {
        this.version = version;
        return this;
    }

    public int getLength() {
        return length;
    }

    public Header setLength(int length) {
        this.length = length;
        return this;
    }

    public long getSessionId() {
        return sessionId;
    }

    public Header setSessionId(long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Byte getType() {
        return type;
    }

    public Header setType(Byte type) {
        this.type = type;
        return this;
    }

    public Byte getPriority() {
        return priority;
    }

    public Header setPriority(Byte priority) {
        this.priority = priority;
        return this;
    }

    public Map<String, String> getAttachment() {
        return attachment;
    }

    public Header setAttachment(Map<String, String> attachment) {
        this.attachment = attachment;
        return this;
    }

    @Override
    public String toString() {
        return "Header{" +
                "version=" + version +
                ", length=" + length +
                ", sessionId=" + sessionId +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
    public static void main(String args[]){
        Header header=new Header();
        header.setType(Header.TYPE_BI_2R);
        System.out.println(header);
    }
}
