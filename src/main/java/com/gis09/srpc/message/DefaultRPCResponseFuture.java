package com.gis09.srpc.message;

import com.gis09.srpc.remoting.RemotingException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huyoufu
 * @TIME 2016年11月02日 11:38
 * @description 默认的future实现
 */
public class DefaultRPCResponseFuture implements RPCResponseFuture {
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();


    private volatile RPCResponse response;
    @Override
    public Object get() throws RemotingException {
        return get(1000);
    }

    @Override
    public Object get(int timeoutInMillis) throws RemotingException {
        if (!isDone()){
            //如果没有完成 也就是说还没有结果 拿到锁
            long start = System.currentTimeMillis();
            lock.lock();
            try {
                while (!isDone()){
                    //然后等待一个超时时间段 注意await操作是非阻塞的 所有会在一个while循环里
                    done.await(timeoutInMillis, TimeUnit.NANOSECONDS);
                    //等待完成之后
                    if (isDone()||System.currentTimeMillis()-start>timeoutInMillis) break;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
            //如果还没有完成 说明超时了
            if (! isDone()) {
                throw new RuntimeException("超时");
            }
        }
        return response;
    }

    @Override
    public boolean isDone() {
        return response!=null;
    }
    private void  doReceived(RPCResponse res){
        lock.lock();
        try {
            response=res;
            if (done!=null)
            done.signal();
        }finally {
            lock.unlock();
        }

    }
    public static void main(String[] args) throws RemotingException {
        DefaultRPCResponseFuture future=new DefaultRPCResponseFuture();
        new Thread(new Putter(future)).start();
        Object o = future.get(1000);
        Object o1 = future.get(1000);
        System.out.println(o1);
    }
    private static class  Putter implements Runnable{
        private DefaultRPCResponseFuture future;
        public Putter(DefaultRPCResponseFuture future){
            this.future=future;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RPCResponse rpcResponse=new RPCResponse();
            rpcResponse.setResult("结果");
            future.doReceived(rpcResponse);
        }
    }
}
