package patterns.factory.abst;

import patterns.factory.abst.car.Car;
import patterns.factory.abst.car.CarFactory;

public class SedanStore implements CarStore {

    private final CarFactory factory;

    public SedanStore(CarFactory factory) {
        this.factory = factory;
    }

    @Override
    public Car order() {
        return factory.create();
    }
}
