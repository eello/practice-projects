package patterns.observer;

public class Main {

    public static void main(String[] args) {
        Magazine magazine = new Magazine();
        Subscriber subscriber1 = new Subscriber("subscriber1");
        Subscriber subscriber2 = new Subscriber("subscriber2");
        Subscriber subscriber3 = new Subscriber("subscriber3");

        // 구독 신청
        magazine.registerObserver(subscriber1);
        magazine.registerObserver(subscriber2);
        magazine.registerObserver(subscriber3);

        // 새로운 칼럼 등록
        magazine.newColumn();

        System.out.println("--------------------");

        // 구독자3의 구독 해제
        magazine.removeObserver(subscriber3);

        // 새로운 칼럼 등록
        magazine.newColumn();
    }
}
