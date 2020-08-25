package ThreadTest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumeAndProductorTest_blockqueue {


    public static void main(String[] args) {

        final ConsumerAndProductor_V2 consumerAndProduct = new ConsumerAndProductor_V2();


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        consumerAndProduct.increatment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        consumerAndProduct.decreatment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    }
}


class ConsumerAndProductor_V2{

    volatile AtomicInteger num = new AtomicInteger(0);
    volatile AtomicInteger num2 = new AtomicInteger(1);

    BlockingQueue blockingQueue=new ArrayBlockingQueue<AtomicInteger>(1);

    public void increatment() throws  Exception{


        while(true){
            num.incrementAndGet();
            blockingQueue.offer(num,2L, TimeUnit.SECONDS);
            System.out.println("生产者生产一个，num is" + num);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void decreatment() throws  Exception{

        while(true){

            Object poll = blockingQueue.poll(2L, TimeUnit.SECONDS);
            System.out.println("消费者消费一个，num is" + poll);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }



}

