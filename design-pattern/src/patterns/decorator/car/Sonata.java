package patterns.decorator.car;

public class Sonata extends Car {

    public Sonata() {
        this.description = "SONATA";
    }

    @Override
    public int getCost() {
        return 3268;
    }
}
