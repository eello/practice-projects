package patterns.singleton;

public class SynchronizedSingleton {

    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() throws InterruptedException {
        Thread.sleep(1);
    }

    public static synchronized SynchronizedSingleton getInstance() throws InterruptedException {
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }
}
