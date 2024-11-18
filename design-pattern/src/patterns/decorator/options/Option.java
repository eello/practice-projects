package patterns.decorator.options;

import patterns.decorator.car.Car;

public abstract class Option extends Car {

    Car car;

    public abstract String getDescription();
}
