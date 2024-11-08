package eello.notificationgeneration.controller;

import eello.notificationgeneration.dto.request.NotificationGenerationRequestDTO;
import eello.notificationgeneration.dto.response.NotificationGenerationResponseDTO;
import eello.notificationgeneration.service.NotificationGenerationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("noti")
@Slf4j
public class NotificationController {

    private final NotificationGenerationService generationService;

    public NotificationController(NotificationGenerationService generationService) {
        this.generationService = generationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public NotificationGenerationResponseDTO generate(@Valid @RequestBody NotificationGenerationRequestDTO dto) {
        return generationService.generate(dto);
    }
}
