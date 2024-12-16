package patterns.strategy;

public class CreditCardStrategy implements PaymentStrategy {

    @Override
    public void processPayment(int amount) {
        System.out.println("카드 결제");
    }
}
