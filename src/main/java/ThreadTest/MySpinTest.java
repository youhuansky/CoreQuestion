package ThreadTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class SpinTest {

    AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();

    public void lock() {

        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(Thread.currentThread().getName() + "  try to get Lock");
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {

            }

        }

        System.out.println(Thread.currentThread().getName() + "  got the Lock");
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "  Unlock ");
    }

}


public class MySpinTest {

    public static void main(String[] args) throws InterruptedException {
        SpinTest spinTest = new SpinTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                spinTest.lock();
                try {
                    Thread.sleep(5000);
                    spinTest.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                spinTest.lock();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                spinTest.unlock();
            }
        }, "t2").start();


        TimeUnit.SECONDS.sleep(100);
    }
}
