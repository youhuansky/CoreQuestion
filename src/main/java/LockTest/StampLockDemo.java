package LockTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampLockDemo {

    StampedLock stampedLock = new StampedLock();
    int number = 37;

    public void read() {
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + "\t 进入到悲观读代码块，共计需要4秒钟读完");
        for (int i = 1; i <= 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 正在读取中..." + i);
        }

        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t 获得变量值result: " + result);
            System.out.println("写线程没有修改，不可介入。因为stampedLock.readLock(),不可以写入，读写互斥");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        //间隔4秒钟，我们很乐观的认为没有其他修改来修改,标志位不动，美好设想。
        System.out.println("4秒前stampedLock.validate值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        for (int i = 1; i <= 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t正在读取中..." + i + "stampedLock.validate值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        }
        if (!stampedLock.validate(stamp)) {
            System.out.println("-----有人动过数据了，只能重新来一次");
            stamp = stampedLock.readLock();
            result = number;
            System.out.println("重新读取一次最新值result: " + result);
            stampedLock.unlockRead(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t final result: " + result);
    }

    public void write() {
        System.out.println(Thread.currentThread().getName() + "\t 进入write方法");
        long stamp = stampedLock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 写线程开始修改");
            number = number + 13;
            System.out.println(Thread.currentThread().getName() + "\t 写线程结束修改");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    public static void main(String[] args) {

        StampLockDemo resource = new StampLockDemo();
        new Thread(() -> {
//            resource.read();
            resource.tryOptimisticRead();
        }, "readThread").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            resource.write();
        }, "writethread").start();

        //暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(7);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            resource.write();
        }, "writethread2").start();
    }

}
