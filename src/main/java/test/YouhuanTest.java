package test;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class YouhuanTest {


    public static void main(String[] args) {
        System.out.println("asdsadasd");
    }

    public int findRepeatNumber(int[] nums) {
        Set<Integer> integers = new HashSet();
        for (int num : nums) {
            if (!integers.contains(num)) {
                integers.add(num);
            } else {
                return num;
            }
        }
        return 0;
    }

    @Test
    public void test6() {

        int num = 6;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);
        Random random = new Random();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " start,time->" + System.currentTimeMillis());
                    Thread.sleep(random.nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + " exit,time->" + System.currentTimeMillis());
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < num - 1; i++) {
            new Thread(task).start();
        }
        System.out.println("main thread start await,time->" + System.currentTimeMillis());
        try {
            cyclicBarrier.await();
            System.out.println("all thread start,time->" + System.currentTimeMillis());
            cyclicBarrier.await();
            System.out.println("main thread end,time->" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }
}
