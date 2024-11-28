package patterns.factory.method.car;

import patterns.factory.Car;

public class RightHandleSedan implements Car {

    @Override
    public String description() {
        return "Right Handle Sedan";
    }
}
