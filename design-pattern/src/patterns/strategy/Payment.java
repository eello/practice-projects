package patterns.strategy;

import java.util.Map;

public class Payment {

    private final Map<String, PaymentStrategy> paymentStrategyMap;

    public Payment(Map<String, PaymentStrategy> paymentStrategyMap) {
        this.paymentStrategyMap = paymentStrategyMap;
    }

    public void pay(PaymentMethod method, int amount) {
        PaymentStrategy paymentStrategy = paymentStrategyMap.get(method.getStrategy());
        if (paymentStrategy != null) {
            paymentStrategy.processPayment(amount);
        }
    }
}
