package eello.notificationlookup.service;

import eello.notificationlookup.dto.response.NotificationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationLookupService {

    /**
     * 최근 90일 내의 알림 조회
     */
    Page<NotificationResponseDTO> lookup(long userId, Pageable pageable);
}
