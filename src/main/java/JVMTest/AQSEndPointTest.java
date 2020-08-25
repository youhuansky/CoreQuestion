package JVMTest;

import java.util.concurrent.locks.ReentrantLock;

public class AQSEndPointTest {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        try {
            lock.lock();
            lock.lock();
        } catch (Exception e) {

        } finally {
            lock.unlock();
            lock.unlock();
        }

    }
}
