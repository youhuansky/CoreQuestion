package ThreadTest;

class MyOrderResource1  implements  Runnable{

    public static volatile  boolean flag=false;
    @Override
    public void run() {
        while(!flag){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName()+"线程一正在执行");

        MyOrderResource2.flag=true;
    }
}

class MyOrderResource2  implements  Runnable{

    volatile  static boolean flag=false;
    @Override
    public void run() {
        while(!flag){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"线程二正在执行");

        MyOrderResource3.flag=true;
    }
}

class MyOrderResource3  implements  Runnable{

    volatile  static boolean flag=false;
    @Override
    public void run() {
        while(!flag){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"线程三正在执行");


    }
}


/**
 *  指定顺序让三个线程顺序执行
 */
public class ThreadOrderTest1 {

    public static void main(String[] args) {
        MyOrderResource1 myOrderResource1 = new MyOrderResource1();
        MyOrderResource2 myOrderResource2 = new MyOrderResource2();
        MyOrderResource3 myOrderResource3 = new MyOrderResource3();
        myOrderResource1.flag=true;
        new Thread(myOrderResource2,"t2").start();
        new Thread(myOrderResource1,"t1").start();
        new Thread(myOrderResource3,"t3").start();




    }


}
