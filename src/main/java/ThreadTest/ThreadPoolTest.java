package ThreadTest;

import java.util.concurrent.*;

public class ThreadPoolTest {


    public static void main(String[] args) {
        try {
            ExecutorService executorService = Executors.newCachedThreadPool();
            FutureTask<Integer> futureTask = new FutureTask((Callable<Integer>) () -> {
                System.out.println("12312321");
                Thread.sleep(3000);
                return 4;
            });
            executorService.submit(futureTask);
            System.out.println("zxcvzxvc");
            Integer o = futureTask.get(2000,TimeUnit.MILLISECONDS);
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
