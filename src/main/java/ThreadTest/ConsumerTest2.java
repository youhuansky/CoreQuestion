package ThreadTest;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyConsumerTestRes {
    public LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue(5);
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void produce() {
        while (true) {
            try {
                lbq.put("str");
                System.out.println(Thread.currentThread().getName() + "---生产者生产");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void comsumer() {
        while (true) {

            try {
                String take = lbq.take();
                System.out.println(Thread.currentThread().getName() + "---消费者消费：" + take);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}

public class ConsumerTest2 {


    public static void main(String[] args) {

        MyConsumerTestRes myConsumerTestRes = new MyConsumerTestRes();
        new Thread(() -> {
            myConsumerTestRes.produce();
        }).start();

        new Thread(() -> {
            myConsumerTestRes.produce();
        }).start();
        new Thread(() -> {
            myConsumerTestRes.comsumer();
        }).start();


    }


}
