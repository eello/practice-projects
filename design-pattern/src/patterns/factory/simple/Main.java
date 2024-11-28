package patterns.factory.simple;

public class Main {

    public static void main(String[] args) {
        SimpleCarStore store = new SimpleCarStore(new SimpleCarFactory());

    }
}
