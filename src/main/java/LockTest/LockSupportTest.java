package LockTest;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    //    public static void main(String[] args) {
//
//        Thread t1 = new Thread(() -> {
//
//            System.out.println(Thread.currentThread().getName() + "  come in");
//
//            LockSupport.unpark(Thread.currentThread());
//            LockSupport.park();
//            System.out.println(Thread.currentThread().getName() + "  被唤醒");
//            System.out.println("=================================");
//            System.out.println(Thread.interrupted());
//            Thread.currentThread().interrupt();
//            System.out.println(Thread.interrupted());
//            System.out.println(Thread.interrupted());
//        }, "t1");
//        t1.start();
//
//        try{
//            TimeUnit.SECONDS.sleep(5);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        LockSupport.unpark(t1);
////        t1.interrupt();
//    }
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new ParkThread());
        t.start();
        Thread.sleep(5000); //①
        System.out.println(Thread.currentThread().getName() + "开始唤醒阻塞线程");
    t.interrupt();
//        LockSupport.unpark(t);
        System.out.println(Thread.currentThread().getName() + "结束唤醒");

    }
}

class ParkThread implements Runnable {

    @Override
    public void run() {
        try{
            System.out.println(Thread.currentThread().getName() + "开始阻塞");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "第一次结束阻塞");

            System.out.println(Thread.currentThread().getName() + "1阻塞");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "2阻塞");
            LockSupport.park();
            System.out.println(Thread.interrupted());
            System.out.println(Thread.interrupted());
            System.out.println(Thread.interrupted());
            System.out.println(Thread.interrupted());
            System.out.println(Thread.interrupted());
            System.out.println(Thread.currentThread().getName() + "3阻塞");
            Thread.currentThread().interrupt();
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "4阻塞");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "5阻塞");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "6阻塞");
            LockSupport.park();
            System.out.println("第二次结束阻塞");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
