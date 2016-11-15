package com.gis09;


/**
 * Hello world!
 */
public class App implements Runnable {
    private volatile boolean stop=false;
    public static void main(String[] args) throws InterruptedException {
        App app=new App();
        Thread thread=new Thread(app);
        thread.start();
        Thread.sleep(3000);
        //System.out.println( "Asking thread to stop..." );
        app.stop=true;
        thread.interrupt();
    }


    @Override
    public void run() {
        while (!stop){
            System.out.println("线程运行中");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("stop被设置了");
    }
}
