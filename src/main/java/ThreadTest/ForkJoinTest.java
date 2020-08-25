package ThreadTest;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class DoAdd extends RecursiveTask<Integer> {

    private  Integer begin;
    private  Integer end;
    private  Integer goal=50;

    public DoAdd(Integer begin, Integer end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getName());
        int res=0;
        if(end-begin<=goal){
            for (int i=begin;i<=end;i++){
                res+=i;
            }
        }else{
            int middle=(begin+end)/2;
            DoAdd doAdd = new DoAdd(begin, middle);
            DoAdd doAdd2 = new DoAdd(middle+1, end);
            doAdd.fork();
            doAdd2.fork();

            res=doAdd.join()+doAdd2.join();
        }
        return res;
    }
}
public class ForkJoinTest {


    public static void main(String[] args) {

        DoAdd doAdd = new DoAdd(1, 100);
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = pool.submit(doAdd);
        try {
            System.out.println(submit.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();

    }



}
