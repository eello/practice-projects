package patterns.factory.simple.car;

import patterns.factory.Car;

public class SUV implements Car {

    @Override
    public String description() {
        return "SUV";
    }
}
