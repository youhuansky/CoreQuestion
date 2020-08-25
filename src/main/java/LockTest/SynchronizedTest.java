package LockTest;


import java.util.concurrent.TimeUnit;

class Monitor {


    public void test() {


        synchronized (this) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在执行");
        }
    }

    public void test2() {


        synchronized (new Object()) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在执行ing");
        }
    }


}

public class SynchronizedTest {


    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        new Thread(new Runnable() {
            @Override
            public void run() {
                monitor.test();
            }
        }, "aa").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                monitor.test2();
            }
        }, "bb").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                monitor.test();
            }
        }, "cc").start();


    }
}
