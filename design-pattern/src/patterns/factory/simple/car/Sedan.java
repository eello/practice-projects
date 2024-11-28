package patterns.factory.simple.car;

import patterns.factory.Car;

public class Sedan implements Car {

    @Override
    public String description() {
        return "Sedan";
    }
}
