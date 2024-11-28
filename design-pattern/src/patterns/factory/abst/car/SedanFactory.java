package patterns.factory.abst.car;

public class SedanFactory extends CarFactory {

    public SedanFactory() {
        this.partsFactory = new SedanPartsFactory();
    }

    @Override
    protected Car createCar() {
        return new Sedan();
    }
}
