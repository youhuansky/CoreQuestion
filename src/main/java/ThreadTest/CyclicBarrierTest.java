package ThreadTest;

import java.util.concurrent.*;

class CyclicBarrierResource  implements  Runnable{


    public CyclicBarrierResource(CyclicBarrier cb){
        this.cb=cb;
    }
    CyclicBarrier cb;
    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName()+"从线程开始执行任务1");

        try {
            cb.await();
            System.out.println(cb.getNumberWaiting()+"在等待");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"从线程开始执行任务2");
    }
}



public class CyclicBarrierTest {


    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

        for(int i=0;i<10;i++){
            threadPoolExecutor.submit(new CyclicBarrierResource(cyclicBarrier));
        }
//        System.out.println("啦啦啦+主线程开始执行！！！");


    }
}
