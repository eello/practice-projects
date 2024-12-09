package patterns.template_method;

public abstract class CarFactory {

    public Car make(String color) {
        Car car = create();
        assemble(car);
        painting(car, color);

        return car;
    }

    public abstract Car create();
    public abstract void assemble(Car car);
    public abstract void painting(Car car, String color);
}
