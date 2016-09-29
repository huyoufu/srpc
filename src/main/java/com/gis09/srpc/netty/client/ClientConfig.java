package com.gis09.srpc.netty.client;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ClientConfig {
    public static final long DEFAULT_HEART_INTERVAL = 45; // 默认的心跳间隔是45秒
    public static final long DEFAULT_RECONNECT_INTERVAL = 15*1000;// 重连间隔
    public static final long DEFAULT_RECONNECT_NUM = 5;// 默认的重试次数 重试五次放弃
    public static final String DEFAULT_SERVER_HOST="127.0.0.1"; //默认绑定的地址
    public static final int DEFAULT_SERVER_PORT=52018;//默认的启动端口
    public static final int DEFAULT_READ_TIME_OUT=20; //读默认超时时间
    public static final int DEFAULT_READ_WRITE_OUT=20;//写默认超时时间
    /**
     * 心跳间隔
     */
    private long HeartInterval=DEFAULT_HEART_INTERVAL;
    /**
     * 重连间隔
     */
    private long reconnectInterval=DEFAULT_RECONNECT_INTERVAL;
    /**
     * 重连次数
     */
    private long reconnectNum=DEFAULT_RECONNECT_NUM;
    /**
     * 服务器uri
     */
    private String serverHost=DEFAULT_SERVER_HOST;
    /**
     * 服务器端口号
     */
    private int serverPort=DEFAULT_SERVER_PORT;
    /**
     * 读超时时间 单位秒
     */
    private int readTimeOut=DEFAULT_READ_TIME_OUT;
    /**
     * 写超时时间 单位秒
     */
    private int writeTimeOut=DEFAULT_READ_WRITE_OUT;
    /**
     * 客户端用户名
     */
    private String userName;
    /**
     * 客户端密码
     */
    private String password;
    /**
     * 是否需要密码验证 默认是不需要验证的
     */
    private boolean needAuth=Boolean.FALSE;
    public long getHeartInterval() {
        return HeartInterval;
    }
    public void setHeartInterval(long heartInterval) {
        HeartInterval = heartInterval;
    }
    public long getReconnectInterval() {
        return reconnectInterval;
    }
    public void setReconnectInterval(long reconnectInterval) {
        this.reconnectInterval = reconnectInterval;
    }
    public long getReconnectNum() {
        return reconnectNum;
    }
    public void setReconnectNum(long reconnectNum) {
        this.reconnectNum = reconnectNum;
    }
    public String getServerHost() {
        return serverHost;
    }
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }
    public int getServerPort() {
        return serverPort;
    }
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isNeedAuth() {
        return needAuth;
    }
    public void setNeedAuth(boolean needAuth) {
        this.needAuth = needAuth;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }
    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }
    public int getWriteTimeOut() {
        return writeTimeOut;
    }
    public void setWriteTimeOut(int writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }
    @Override
    public String toString() {
        return "ClientConfig [HeartInterval=" + HeartInterval + ", reconnectInterval=" + reconnectInterval
                + ", reconnectNum=" + reconnectNum + ", serverHost=" + serverHost + ", serverPort=" + serverPort
                + ", readTimeOut=" + readTimeOut + ", writeTimeOut=" + writeTimeOut + ", userName=" + userName
                + ", password=" + password + ", needAuth=" + needAuth + "]";
    }
    public ClientConfig(long heartInterval, long reconnectInterval, long reconnectNum, String serverHost,
                        int serverPort, int readTimeOut, int writeTimeOut, String userName, String password, boolean needAuth) {
        super();
        HeartInterval = heartInterval;
        this.reconnectInterval = reconnectInterval;
        this.reconnectNum = reconnectNum;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.readTimeOut = readTimeOut;
        this.writeTimeOut = writeTimeOut;
        this.userName = userName;
        this.password = password;
        this.needAuth = needAuth;
    }
    public ClientConfig(String userName, String password, boolean needAuth) {
        super();
        this.userName = userName;
        this.password = password;
        this.needAuth = needAuth;
    }
    public ClientConfig(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    public ClientConfig(String serverHost) {
        this.serverHost = serverHost;
    }

    public ClientConfig(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public ClientConfig() {
    }
}
