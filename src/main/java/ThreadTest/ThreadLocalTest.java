package ThreadTest;

public class ThreadLocalTest {
    static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
    static ThreadLocal<String> stringThreadLocal2 = new ThreadLocal<>();
    static ThreadLocal<String> stringThreadLocal3 = new ThreadLocal<>();

    public static void main(String[] args) {

        stringThreadLocal.set("aaaaaaaaazzzzzzzzzzz");
        stringThreadLocal2.set("zcvcxvxvasdas");
        stringThreadLocal3.set("yyyyyywewerwz");
        System.out.println(stringThreadLocal);
        System.out.println(stringThreadLocal.get());
        System.out.println(stringThreadLocal2.get());
        System.out.println(stringThreadLocal3.get());
    }
}
