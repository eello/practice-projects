package patterns.strategy;

public enum PaymentMethod {

    NAVERPAY("naverPayStrategy"),
    TOSSPAY("tossPayStrategy"),
    CREDITCARD("creditCardStrategy"),
    ;

    private String strategy;

    PaymentMethod(String strategy) {
        this.strategy = strategy;
    }

    public String getStrategy() {
        return strategy;
    }
}
