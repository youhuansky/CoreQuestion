package ThreadTest;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone{


   static Lock lock=new ReentrantLock();
    static Lock lock2=new ReentrantLock();
    public   void sendSMS() {
        try {
            lock.lock();
            Thread.sleep(4000);
        } catch (Exception e) {
        }finally {
            lock.unlock();
        }
        System.out.println("发短信");
    }
    public  static void sendEmail() {
        try{
            lock.lock();
            System.out.println("发邮件");
        }catch(Exception e){

        }finally{
            lock.unlock();
        }


    }

    public  void openPhone() {

        System.out.println("开机");

    }

}

public class EightLockTest {


    public static void main(String[] args) {

        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(new Runnable() {
           @Override
           public void run() {
               phone.sendSMS();
           }
        },"A").start();

        try{
            Thread.sleep(1000);
        }catch(Exception e){

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                phone2.sendEmail();
            }
        },"B").start();


    }
}
