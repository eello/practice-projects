package eello.notification.service;

import eello.notification.dto.request.NotificationCreateRequestDTO;
import eello.notification.dto.response.NotificationCreateResponseDTO;

public interface NotificationService {

    /**
     * 알림을 생성하고 데이터베이스에 저장하는 메서드
     */
    NotificationCreateResponseDTO create(NotificationCreateRequestDTO dto);
}
