package patterns.template_method;

public abstract class Car {

    private String type;

    public Car(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
