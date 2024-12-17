package eello.notificationgeneration.service;

import com.rabbitmq.client.Channel;
import eello.notificationgeneration.dto.request.NotificationGenerationRequestMessage;
import eello.notificationgeneration.entity.Notification;
import eello.notificationgeneration.repository.GenerateRequestRepository;
import eello.notificationgeneration.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationGenerationWorkerImpl {

    private final GenerateRequestRepository requestRepository;
    private final NotificationRepository notificationRepository;
    private final RetryTemplate retryTemplate;

    @RabbitListener(queues = "${spring.rabbitmq.queue}", ackMode = "MANUAL")
    public void generate(
            NotificationGenerationRequestMessage message,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        retryTemplate.execute((context) -> {
            // 메시지 처리 로직
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
                channel.basicAck(tag, false);
                return null;
            } catch (Exception e) {
                log.error("generate notification failed: notification id={}", notification.getId());
                throw e;
            }
        }, (context) -> {
            // 재시도 횟수 초과시 실행 로직
            channel.basicNack(tag, false, false);
            log.info("this message[{}] insert to dql", tag);
            return null;
        });
    }
}
