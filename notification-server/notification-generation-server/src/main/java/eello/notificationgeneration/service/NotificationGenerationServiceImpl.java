    package eello.notificationgeneration.service;

    import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
    import eello.notificationgeneration.dto.response.NotificationGenerationResponseDTO;
    import eello.notificationgeneration.entity.Notification;
    import eello.notificationgeneration.repository.NotificationRepository;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationGenerationServiceImpl implements NotificationGenerationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public NotificationGenerationResponseDTO create(NotificationGenerationRequestDTO dto) {
        Assert.notNull(dto, "NotificationCreateRequestDTO must not be null");

        Notification notification = dto.toEntity();
        try {
            notification = notificationRepository.save(notification);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return NotificationGenerationResponseDTO.from(notification);
    }
}
