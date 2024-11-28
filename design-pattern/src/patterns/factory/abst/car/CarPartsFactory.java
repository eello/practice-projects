package patterns.factory.abst.car;

import patterns.factory.abst.car.parts.frame.Frame;
import patterns.factory.abst.car.parts.wheel.Wheel;

public interface CarPartsFactory {

    Wheel createWheel();
    Frame createFrame();
}
