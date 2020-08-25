package ThreadTest;


import java.awt.image.VolatileImage;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyOrderResource2_1 {

    volatile String name = "t1";
    Lock lock = new ReentrantLock();
    Condition con1 = lock.newCondition();
    Condition con2 = lock.newCondition();
    Condition con3 = lock.newCondition();


    public void run1() {
        lock.lock();
        try {
            while (!"t1".equals(name)) {
                con1.await();
            }
            Thread.sleep(2000);
            System.out.println("线程一正在运行");
            name="t2";
            con2.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void run2() {
        lock.lock();
        try {
            while (!"t2".equals(name)) {
                con2.await();
            }
            System.out.println("线程二正在运行");
            Thread.sleep(3000);
            name="t3";
            con3.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void run3() {
        lock.lock();
        try {
            while (!"t3".equals(name)) {
                con3.await();
            }
            System.out.println("线程三正在运行");
            name="t1";
            con1.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }
}


public class ThreadOrderTest2 {


    public static void main(String[] args) {
        MyOrderResource2_1 myOrderResource2_1 = new MyOrderResource2_1();
        new Thread(new Runnable() {
            @Override
            public void run() {
                myOrderResource2_1.run3();
            }
        }, "t3").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                myOrderResource2_1.run1();
            }
        }, "t1").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                myOrderResource2_1.run2();
            }
        }, "t2").start();



    }
}
