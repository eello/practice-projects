package patterns.strategy;

public class NaverPayStrategy implements PaymentStrategy {

    @Override
    public void processPayment(int amount) {
        System.out.println("네이버페이 결제");
    }
}
