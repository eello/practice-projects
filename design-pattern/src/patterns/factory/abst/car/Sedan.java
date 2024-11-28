package patterns.factory.abst.car;

import patterns.factory.CarType;

public class Sedan extends Car {

    public Sedan() {
        this.type = CarType.SEDAN;
    }
}
