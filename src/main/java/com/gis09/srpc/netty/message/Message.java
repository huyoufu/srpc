package com.gis09.srpc.netty.message;

/**
 * Created by huyoufu on 2016/9/28.
 */
public class Message {
    private Header header;
    private BodyWrapper bodyWrapper;

    public Header getHeader() {
        return header;
    }

    public Message setHeader(Header header) {
        this.header = header;
        return this;
    }

    public BodyWrapper getBodyWrapper() {
        return bodyWrapper;
    }

    public void setBodyWrapper(BodyWrapper bodyWrapper) {
        this.bodyWrapper = bodyWrapper;
    }
}
