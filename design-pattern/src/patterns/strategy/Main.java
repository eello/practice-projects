package patterns.strategy;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, PaymentStrategy> paymentStrategyMap = new HashMap<>();
        paymentStrategyMap.put(PaymentMethod.NAVERPAY.getStrategy(), new NaverPayStrategy());
        paymentStrategyMap.put(PaymentMethod.TOSSPAY.getStrategy(), new TossPayStrategy());
        paymentStrategyMap.put(PaymentMethod.CREDITCARD.getStrategy(), new CreditCardStrategy());

        Payment payment = new Payment(paymentStrategyMap);
        payment.pay(PaymentMethod.NAVERPAY, 5000);
    }
}
