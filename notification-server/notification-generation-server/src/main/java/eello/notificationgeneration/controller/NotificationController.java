package eello.notificationgeneration.controller;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
import eello.notificationgeneration.dto.response.NotificationGenerationResponseDTO;
import eello.notificationgeneration.service.NotificationGenerationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("noti")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationGenerationService notificationGenerationService;

    @PostMapping
    public NotificationGenerationResponseDTO createNotification(@Valid @RequestBody NotificationGenerationRequestDTO dto) {
        log.info("Creating notification: {}", dto);
        return notificationGenerationService.create(dto);
    }
}
