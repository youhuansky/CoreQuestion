package MyLeetCode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FooBar {
    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    boolean flag1 = true;

    public void foo(Runnable printFoo) throws InterruptedException {
        lock.lock();
        try {
            for (int i = 0; i < n; i++) {
                while (!flag1) {
                    condition1.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();

                flag1 = false;
                condition2.signal();

            }
        } finally {
            lock.unlock();
        }

    }

    public void bar(Runnable printBar) throws InterruptedException {
        lock.lock();
        try {
            for (int i = 0; i < n; i++) {
                // printBar.run() outputs "bar". Do not change or remove this line.
                while (flag1) {
                    condition2.await();
                }
                printBar.run();
                flag1 = true;
                condition1.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}

public class FooBarTest {

    public static void main(String[] args) {

        FooBar fooBar = new FooBar(1);
        try {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        fooBar.foo(new Runnable() {
                            @Override
                            public void run() {
                                System.out.print("foo");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        fooBar.bar(new Runnable() {
                            @Override
                            public void run() {
                                System.out.print("bar");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}