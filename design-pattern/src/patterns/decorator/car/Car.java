package patterns.decorator.car;

public abstract class Car {
    String description;

    public String getDescription() {
        return description + "(" + getCost() + "만원)";
    }

    public abstract int getCost();
}
