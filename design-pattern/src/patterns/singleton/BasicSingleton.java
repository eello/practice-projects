package patterns.singleton;

public class BasicSingleton {

    private static BasicSingleton instance;

    private BasicSingleton() throws InterruptedException {
        Thread.sleep(1);
    }

    public static BasicSingleton getInstance() throws InterruptedException {
        if (instance == null) {
            instance = new BasicSingleton();
        }
        return instance;
    }
}
