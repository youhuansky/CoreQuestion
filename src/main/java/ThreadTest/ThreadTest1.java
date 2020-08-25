package ThreadTest;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


class MyResource implements Runnable {


    public void run() {
        System.out.println(Thread.currentThread().getName() + "asdzxc");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class ThreadTest1 {


    public static void main(String[] args) {

        MyResource myResource = new MyResource();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 8, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 17; i++) {
            threadPoolExecutor.submit(myResource);
        }

        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getCorePoolSize());
        System.out.println(threadPoolExecutor.getPoolSize());
        System.out.println(threadPoolExecutor.getTaskCount());
        System.out.println(threadPoolExecutor.getQueue().size());

    }
}
