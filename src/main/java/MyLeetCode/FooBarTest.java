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

    public void foo(Runnable printFoo) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 0; i < n; i++) {
                // printFoo.run() outputs "foo". Do not change or remove this line.



                printFoo.run();

                condition2.signal();
                condition1.await();
            }
            condition2.signal();
        } finally {
            lock.unlock();
        }

    }

    public void bar(Runnable printBar) throws InterruptedException {
        try {
            lock.lock();
            for (int i = 0; i < n; i++) {
                // printBar.run() outputs "bar". Do not change or remove this line.


                condition2.await();
                printBar.run();
                condition1.signal();
            }
            condition1.signal();
        } finally {
            lock.unlock();
        }

    }
}

public class FooBarTest {

    public static void main(String[] args) {

        FooBar fooBar = new FooBar(2);
        try {

            fooBar.foo(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });

            fooBar.bar(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}