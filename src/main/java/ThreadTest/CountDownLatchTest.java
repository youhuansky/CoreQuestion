package ThreadTest;

import java.util.concurrent.*;

class CountDownLatchResource implements Runnable {
    CountDownLatch cdl;

    public CountDownLatchResource(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName()+"线程开始执行！！");

        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cdl.countDown();
    }
}

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 2L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(new CountDownLatchResource(countDownLatch));
        }
        countDownLatch.await();
        System.out.println("啦啦啦，主线程开始执行");


    }
}
