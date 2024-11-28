package patterns.factory.method.car;

import patterns.factory.Car;

public class LeftHandleSUV implements Car {

    @Override
    public String description() {
        return "Left Handle SUV";
    }
}
