package MyLeetCode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 我们提供了一个类：
 * public class Foo {
 *   public void one() { print("one"); }
 *   public void two() { print("two"); }
 *   public void three() { print("three"); }
 * }
 * 三个不同的线程将会共用一个 Foo 实例。
 * <p>
 * 线程 A 将会调用 one() 方法
 * 线程 B 将会调用 two() 方法
 * 线程 C 将会调用 three() 方法
 * 请设计修改程序，以确保 two() 方法在 one() 方法之后被执行，three() 方法在 two() 方法之后被执行。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/print-in-order
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class FooTest {

    public FooTest() {

    }

    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    volatile String str ="first";


    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        lock.lock();

        if(!"first".equals(str)){
            condition1.await();
        }
        printFirst.run();
        str ="second";
        condition2.signal();
        lock.unlock();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        lock.lock();
        if(!"second".equals(str)){

            condition2.await();
        }
        printSecond.run();
        str ="third";
        condition3.signal();
        lock.unlock();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        lock.lock();

        if(!"third".equals(str)){
            condition3.await();

        }
        printThird.run();
        str ="first";
        condition1.signal();
        lock.unlock();
    }

    public static void main(String[] args) {

        FooTest fooTest = new FooTest();
        try {

            fooTest.first(new Runnable() {
                @Override
                public void run() {
                    System.out.println("first");
                }
            });

            fooTest.second(new Runnable() {
                @Override
                public void run() {
                    System.out.println("second");
                }
            });

            fooTest.third(new Runnable() {
                @Override
                public void run() {
                    System.out.println("third");
                }
            });




        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
