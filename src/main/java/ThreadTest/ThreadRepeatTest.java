package ThreadTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class ThreadRepeatResource implements Runnable {


    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(2);

    @Override
    public void run() {

        while (true) {
            Runnable poll = null;
            try {
                poll = queue.poll(2L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            poll.run();
        }

    }
}


public class ThreadRepeatTest {


    public static void main(String[] args) {

        ThreadRepeatResource resource = new ThreadRepeatResource();

        try {


            new Thread(resource, "lalala").start();
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int j = i;
                resource.queue.offer(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName()+"-----------" + j);
                    }
                }, 2L, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


}
