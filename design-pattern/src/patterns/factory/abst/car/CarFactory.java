package patterns.factory.abst.car;

public abstract class CarFactory {

    protected CarPartsFactory partsFactory;

    public Car create() {
        Car car = createCar();
        car.assemble()
                .frame(partsFactory.createFrame())
                .wheel(partsFactory.createWheel());

        return car;
    }

    protected abstract Car createCar();
}
