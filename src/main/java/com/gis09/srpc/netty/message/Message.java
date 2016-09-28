package com.gis09.srpc.netty.message;

/**
 * Created by huyoufu on 2016/9/28.
 */
public class Message {
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public Message setHeader(Header header) {
        this.header = header;
        return this;
    }

    public Object getBody() {
        return body;
    }

    public Message setBody(Object body) {
        this.body = body;
        return this;
    }
}
