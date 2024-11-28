package patterns.factory.method.car;

import patterns.factory.Car;

public class RightHandleSUV implements Car {

    @Override
    public String description() {
        return "Right Handle SUV";
    }
}
