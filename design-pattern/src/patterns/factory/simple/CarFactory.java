package patterns.factory.simple;

import patterns.factory.Car;
import patterns.factory.CarType;

public interface CarFactory {

    Car createCar(CarType type);
}
