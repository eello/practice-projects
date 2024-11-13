package patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class Magazine implements Subject {

    private List<Observer> subscribers = new ArrayList<>();

    public void newColumn() {
        System.out.println("<새로운 칼럼 등록>");
        notifyObservers(); // 구독자들에게 새로운 갈럼이 등록되었음을 알림
    }

    @Override
    public void registerObserver(Observer observer) {
        subscribers.add(observer); // 구독자 등록
    }

    @Override
    public void removeObserver(Observer observer) {
        subscribers.remove(observer); // 구독자 해제
    }

    @Override
    public void notifyObservers() {
        for (Observer subscriber : subscribers) {
            subscriber.update(); // 구독자가 정의한 행동 실행
        }
    }
}
