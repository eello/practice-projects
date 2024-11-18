package patterns.decorator.options;

import patterns.decorator.car.Car;

public class LEDLighting extends Option {

    public static final int COST = 25;

    Car car;

    public LEDLighting(Car car) {
        this.car = car;
    }

    @Override
    public String getDescription() {
        return car.getDescription() + " + LED 라이팅(" + COST + "만원)";
    }

    @Override
    public int getCost() {
        return car.getCost() + COST;
    }
}
