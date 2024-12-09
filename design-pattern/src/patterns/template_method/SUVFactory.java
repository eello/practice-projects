package patterns.template_method;

public class SUVFactory extends CarFactory {

    private static final String TYPE = "SUV";

    @Override
    public Car create() {
        return new SUV(TYPE);
    }

    @Override
    public void assemble(Car car) {
        System.out.println(car.getType() + " 차량 조립.");
    }

    @Override
    public void painting(Car car, String color) {
        System.out.println(car.getType() + " 차량 - " + color +"로 도색.");
    }
}
