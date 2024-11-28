package patterns.factory.method.car;

import patterns.factory.Car;

public class LeftHandleSedan implements Car {

    @Override
    public String description() {
        return "Left Handle Sedan";
    }
}
