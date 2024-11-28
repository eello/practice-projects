package patterns.factory.simple;

import patterns.factory.Car;
import patterns.factory.CarType;
import patterns.factory.simple.car.SUV;
import patterns.factory.simple.car.Sedan;

public class SimpleCarFactory implements CarFactory {

    @Override
    public Car createCar(CarType type) {
        return switch (type) {
            case SEDAN -> new Sedan();
            case SUV -> new SUV();
        };
    }
}
