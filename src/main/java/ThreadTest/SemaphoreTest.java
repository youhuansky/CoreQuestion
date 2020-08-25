package ThreadTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class SemaphoreResource implements  Runnable{

    public Semaphore sp;
    public SemaphoreResource(Semaphore sp){
        this.sp=sp;
    }

    @Override
    public void run() {
        try {
            sp.acquire();
            System.out.println(Thread.currentThread().getName()+"线程开始执行");

            TimeUnit.SECONDS.sleep(3L);


            sp.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }





}



public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        for (int i = 0; i <20 ; i++) {
            threadPoolExecutor.submit(new SemaphoreResource(semaphore));
        }



    }



}
