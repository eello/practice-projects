package patterns.singleton;

public class DCLSingleton {

    private volatile static DCLSingleton instance;

    private DCLSingleton() throws InterruptedException {
        Thread.sleep(1);
    }

    public static DCLSingleton getInstance() throws InterruptedException {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
