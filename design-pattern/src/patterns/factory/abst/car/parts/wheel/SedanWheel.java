package patterns.factory.abst.car.parts.wheel;

public class SedanWheel implements Wheel {

    @Override
    public void display() {
        System.out.println("left handle");
    }
}
