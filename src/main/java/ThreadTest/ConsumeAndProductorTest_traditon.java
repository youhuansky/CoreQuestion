package ThreadTest;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumeAndProductorTest_traditon {

    public static void main(String[] args) {
        final ConsumerAndProductor consumerAndProduct = new ConsumerAndProductor();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    consumerAndProduct.increatment();
                }
            }).start();
        }


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    consumerAndProduct.decreatment();
                }
            }).start();
        }
    }

}


class ConsumerAndProductor {


    volatile AtomicInteger num = new AtomicInteger(0);
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void increatment() {
        lock.lock();
        try {
            while (num.get() == 1) {

                condition.await();
            }
            num.incrementAndGet();
            System.out.println("生产者生产一个，num is" + num);
            condition.signal();
        } catch (Exception e) {


        }finally {
            lock.unlock();
        }


    }

    public void decreatment() {

        lock.lock();
        try {
            while (num.get() == 0) {

                condition.await();
            }

            num.decrementAndGet();
            System.out.println("消费者消费一个，num is" + num);
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }


}
