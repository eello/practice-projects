    package eello.notification.service;

import eello.notification.dto.request.NotificationCreateRequestDTO;
import eello.notification.dto.response.NotificationCreateResponseDTO;
import eello.notification.dto.response.NotificationResponseDTO;
import eello.notification.entity.Notification;
import eello.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public NotificationCreateResponseDTO create(NotificationCreateRequestDTO dto) {
        Assert.notNull(dto, "NotificationCreateRequestDTO must not be null");

        Notification notification = dto.toEntity();
        try {
            notification = notificationRepository.save(notification);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return NotificationCreateResponseDTO.from(notification);
    }

    @Override
    public Page<NotificationResponseDTO> find(long userId, int page, int size) {
        Assert.isTrue(page > 0, "page must be greater than 0");
        Assert.isTrue(size >= 0, "size must be greater than 0");

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("requestAt").descending());
        Page<Notification> found = notificationRepository.findByUserId(userId, pageRequest);
        return found.map(NotificationResponseDTO::from);
    }
}
