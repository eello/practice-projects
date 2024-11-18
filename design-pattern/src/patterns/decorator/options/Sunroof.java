package patterns.decorator.options;

import patterns.decorator.car.Car;

public class Sunroof extends Option {

    public static final int COST = 119;

    public Sunroof(Car car) {
        this.car = car;
    }


    @Override
    public String getDescription() {
        return car.getDescription() + " + 선루프(" + COST + "만원)";
    }

    @Override
    public int getCost() {
        return car.getCost() + COST;
    }
}
