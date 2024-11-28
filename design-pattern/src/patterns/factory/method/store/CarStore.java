package patterns.factory.method.store;

import patterns.factory.Car;
import patterns.factory.CarType;

public abstract class CarStore {

    public Car order(CarType type) {
        Car car = createCar(type);
        System.out.println("created " + car.description());
        return car;
    }

    protected abstract Car createCar(CarType type);
}
