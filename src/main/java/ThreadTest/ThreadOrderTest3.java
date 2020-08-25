package ThreadTest;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyOrderResource3_1 implements Runnable {

    volatile String name = "pool-1-thread-1";
    Lock lock = new ReentrantLock();
    Condition con1 = lock.newCondition();
    Condition con2 = lock.newCondition();
    Condition con3 = lock.newCondition();

    @Override
    public void run() {
        lock.lock();
        try {


            if (!"pool-1-thread-1".equals(name)) {
                while (!"pool-1-thread-1".equals(Thread.currentThread().getName())) {
                    con1.await();
                }
            }
            if (!"pool-1-thread-2".equals(Thread.currentThread().getName())) {
                while (!"pool-1-thread-2".equals(Thread.currentThread().getName())) {
                    con2.await();
                }
            }
            if (!"pool-1-thread-3".equals(Thread.currentThread().getName())) {
                while (!"pool-1-thread-3".equals(Thread.currentThread().getName())) {
                    con3.await();
                }
            }
            System.out.println(Thread.currentThread().getName() + "正在执行");

            if("pool-1-thread-1".equals(name)){
                con2.signal();
            }else if("pool-1-thread-2".equals(name)){
                con3.signal();
            }else if("pool-1-thread-3".equals(name)){
                con1.signal();
            }


        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}


public class ThreadOrderTest3 {


    public static void main(String[] args) {
        MyOrderResource3_1 myOrderResource3_1 = new MyOrderResource3_1();
        MyOrderResource3_1 myOrderResource3_2 = new MyOrderResource3_1();
        MyOrderResource3_1 myOrderResource3_3 = new MyOrderResource3_1();
        BlockingQueue<Runnable> runnables = new LinkedBlockingQueue<>(2);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 3, 1000, TimeUnit.SECONDS, runnables);
        poolExecutor.submit(myOrderResource3_1);
        poolExecutor.submit(myOrderResource3_2);
        poolExecutor.submit(myOrderResource3_3);

    }


}
