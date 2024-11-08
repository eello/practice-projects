package eello.notificationgeneration.service;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
import eello.notificationgeneration.dto.request.NotificationGenerationRequestMessage;
import eello.notificationgeneration.dto.response.NotificationGenerationResponseDTO;
import eello.notificationgeneration.entity.RequestInfo;
import eello.notificationgeneration.repository.GenerateRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationAsyncGenerationService implements NotificationGenerationService {

    private final GenerateRequestRepository requestRepository;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public NotificationGenerationResponseDTO generate(NotificationGenerationRequestDTO dto) {
        RequestInfo requestInfo = dto.toRequestInfo();
        Long requestId = requestRepository.saveRequest(requestInfo); // 요청 정보 저장

        NotificationGenerationRequestMessage message =
                NotificationGenerationRequestMessage.of(requestId, dto); // 생성 요청 메시지 생성
        rabbitTemplate.convertAndSend(message); // 생성 요청 메시지 발행

        log.info("Issue to RabbitMQ for Notification Generation Request: {}", dto);

        return NotificationGenerationResponseDTO.of(requestId);
    }
}
