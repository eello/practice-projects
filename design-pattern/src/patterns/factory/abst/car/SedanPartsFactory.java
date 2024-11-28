package patterns.factory.abst.car;

import patterns.factory.abst.car.parts.frame.Frame;
import patterns.factory.abst.car.parts.frame.SedanFrame;
import patterns.factory.abst.car.parts.wheel.SedanWheel;
import patterns.factory.abst.car.parts.wheel.Wheel;

public class SedanPartsFactory implements CarPartsFactory {

    @Override
    public Wheel createWheel() {
        return new SedanWheel();
    }

    @Override
    public Frame createFrame() {
        return new SedanFrame();
    }
}
