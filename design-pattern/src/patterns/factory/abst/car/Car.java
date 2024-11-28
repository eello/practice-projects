package patterns.factory.abst.car;

import patterns.factory.CarType;
import patterns.factory.abst.car.parts.frame.Frame;
import patterns.factory.abst.car.parts.wheel.Wheel;

public abstract class Car {

    private Wheel wheel;
    private Frame frame;
    protected CarType type;

    public Car assemble() {
        return this;
    }

    public Car wheel(Wheel wheel) {
        this.wheel = wheel;
        return this;
    }

    public Car frame(Frame frame) {
        this.frame = frame;
        return this;
    }
}
