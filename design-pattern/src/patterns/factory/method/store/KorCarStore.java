package patterns.factory.method.store;

import patterns.factory.Car;
import patterns.factory.method.car.LeftHandleSUV;
import patterns.factory.method.car.LeftHandleSedan;
import patterns.factory.CarType;

public class KorCarStore extends CarStore {

    @Override
    protected Car createCar(CarType type) {
        return switch (type) {
            case SEDAN -> new LeftHandleSedan();
            case SUV -> new LeftHandleSUV();
        };
    }
}
