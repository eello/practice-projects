package eello.notification.service;

import eello.notification.dto.request.NotificationCreateRequestDTO;
import eello.notification.dto.response.NotificationCreateResponseDTO;
import eello.notification.dto.response.NotificationResponseDTO;
import org.springframework.data.domain.Page;

public interface NotificationService {

    /**
     * 알림을 생성하고 데이터베이스에 저장하는 메서드
     */
    NotificationCreateResponseDTO create(NotificationCreateRequestDTO dto);

    /**
     * 최근 90일 간의 알림 조회
     */
    Page<NotificationResponseDTO> find(long userId, int size, int page);
}
