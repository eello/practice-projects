package patterns.decorator;

import patterns.decorator.car.Car;
import patterns.decorator.car.Sonata;
import patterns.decorator.options.LEDLighting;
import patterns.decorator.options.ParkingAssistant;
import patterns.decorator.options.Sunroof;

public class Main {

    public static void main(String[] args) {
        Car myCar = new Sonata();
        myCar = new Sunroof(myCar);
        myCar = new ParkingAssistant(myCar);
        myCar = new LEDLighting(myCar);

        System.out.println("<my car>");
        System.out.println(myCar.getDescription());
        System.out.println("cost = " + myCar.getCost());

        System.out.println("-------------------------------");

        Car broCar = new Sonata();
        broCar = new ParkingAssistant(broCar);
        broCar = new LEDLighting(broCar);

        System.out.println("<bro car>");
        System.out.println(broCar.getDescription());
        System.out.println("cost = " + broCar.getCost());
    }
}
