package patterns.factory.method.store;

import patterns.factory.Car;
import patterns.factory.CarType;
import patterns.factory.method.car.RightHandleSUV;
import patterns.factory.method.car.RightHandleSedan;

public class UKCarStore extends CarStore {

    @Override
    protected Car createCar(CarType type) {
        return switch (type) {
            case SEDAN -> new RightHandleSedan();
            case SUV -> new RightHandleSUV();
        };
    }
}
