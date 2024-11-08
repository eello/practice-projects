package eello.notificationgeneration.service;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
import eello.notificationgeneration.dto.request.NotificationGenerationRequestMessage;
import eello.notificationgeneration.entity.Notification;
import eello.notificationgeneration.repository.GenerateRequestRepository;
import eello.notificationgeneration.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationGenerationWorkerImpl implements NotificationGenerationWorker {

    private final GenerateRequestRepository requestRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void generate(NotificationGenerationRequestMessage message) {
        Notification notification = message.toEntity();

        try {
            /**
             * save 가 아닌 saveAndFlush 를 사용한 이유
             * 알림을 데이터베이스에 반영한 시점이 알림 생성 성공이라고 생각한다.
             * save 메서드만을 사용한다면
             * 영속성 컨텍스트에 엔티티 저장 -> redis 내 request 성공여부 업데이트
             * 순으로 진행되기 때문에 이후에 쿼리가 데이터베이스에 flush 될 때 에러가 발생하더라도
             * redis 의 변경사항은 그대로 유지된다.
             * 이런 이유로 flush 까지 해서 에러가 없는 경우 redis 에 성공여부를 업데이트 하기 위해 saveAndFlush 메서드를 사용했다.
             */
            notification = notificationRepository.saveAndFlush(notification);
            requestRepository.successRequest(message.getRequestId());
        } catch (Exception e) {
            log.error("generate notification failed: requestId={}", notification.getId());
            log.error(e.getMessage());
        }
    }
}
