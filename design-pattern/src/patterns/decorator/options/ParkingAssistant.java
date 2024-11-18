package patterns.decorator.options;

import patterns.decorator.car.Car;

public class ParkingAssistant extends Option {

    public static final int COST = 168;

    public ParkingAssistant(Car car) {
        this.car = car;
    }

    @Override
    public String getDescription() {
        return car.getDescription() + " + 파킹 어시스턴트(" + COST + "만원)";
    }

    @Override
    public int getCost() {
        return car.getCost() + COST;
    }
}
