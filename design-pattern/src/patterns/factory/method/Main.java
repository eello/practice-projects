package patterns.factory.method;

import patterns.factory.Car;
import patterns.factory.method.store.CarStore;
import patterns.factory.method.store.KorCarStore;
import patterns.factory.method.store.UKCarStore;
import patterns.factory.CarType;

public class Main {

    public static void main(String[] args) {
        CarStore kor = new KorCarStore();
        CarStore uk = new UKCarStore();

        Car korSedan = kor.order(CarType.SEDAN);
        Car ukSedan = uk.order(CarType.SEDAN);
    }
}
