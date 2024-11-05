package eello.notification.controller;

import eello.notification.dto.request.NotificationCreateRequestDTO;
import eello.notification.dto.response.NotificationCreateResponseDTO;
import eello.notification.dto.response.NotificationResponseDTO;
import eello.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("noti")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationCreateResponseDTO createNotification(@Valid @RequestBody NotificationCreateRequestDTO dto) {
        log.info("Creating notification: {}", dto);
        return notificationService.create(dto);
    }

    @GetMapping("/{userId}")
    public Page<NotificationResponseDTO> getAllNotifications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return notificationService.find(userId, page, size);
    }
}
