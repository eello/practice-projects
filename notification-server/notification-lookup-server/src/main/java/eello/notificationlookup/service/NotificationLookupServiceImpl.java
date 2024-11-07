package eello.notificationlookup.service;

import eello.notificationlookup.dto.response.NotificationResponseDTO;
import eello.notificationlookup.entitiy.Notification;
import eello.notificationlookup.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class NotificationLookupServiceImpl implements NotificationLookupService {

    private final NotificationRepository notificationRepository;

    @Override
    public Page<NotificationResponseDTO> lookup(long userId, Pageable pageable) {
        Assert.notNull(pageable, "Pageable must not be null");

        Page<Notification> notifications = notificationRepository.findByUserId(userId, pageable);
        return notifications.map(NotificationResponseDTO::from);
    }
}
