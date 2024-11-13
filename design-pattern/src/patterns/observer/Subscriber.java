package patterns.observer;

public class Subscriber implements Observer {

    private String id;

    public Subscriber(String id) {
        this.id = id;
    }

    @Override
    public void update() {
        System.out.println(id + ": 칼럼 읽기");
    }
}
