package eello.notificationgeneration.service;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
import eello.notificationgeneration.dto.response.NotificationGenerationResponseDTO;

public interface NotificationGenerationService {

    /**
     * 알림을 생성하고 데이터베이스에 저장하는 메서드
     */
    NotificationGenerationResponseDTO create(NotificationGenerationRequestDTO dto);
}
