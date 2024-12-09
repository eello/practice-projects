package patterns.singleton;

public class StaticHolderSingleton {

    private StaticHolderSingleton() {

    }

    public static StaticHolderSingleton getInstance() {
        return StaticHolder.instance;
    }

    private class StaticHolder {
        private static StaticHolderSingleton instance = new StaticHolderSingleton();
    }
}
