package patterns.strategy;

public class TossPayStrategy implements PaymentStrategy {

    @Override
    public void processPayment(int amount) {
        System.out.println("토스페이 결제");
    }
}
