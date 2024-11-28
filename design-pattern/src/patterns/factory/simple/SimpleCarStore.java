package patterns.factory.simple;

import patterns.factory.Car;
import patterns.factory.CarType;
import patterns.factory.simple.car.SUV;
import patterns.factory.simple.car.Sedan;

public class SimpleCarStore {

    private final CarFactory factory;

    public SimpleCarStore() {
        this(new SimpleCarFactory());
    }

    public SimpleCarStore(CarFactory factory) {
        this.factory = factory;
    }

    public Car order(CarType type) {
        return factory.createCar(type);
    }
}
