package eello.notificationlookup.controller;

import eello.notificationlookup.dto.response.NotificationResponseDTO;
import eello.notificationlookup.service.NotificationLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("noti")
@RequiredArgsConstructor
public class NotificationLookupController {

    private final NotificationLookupService lookupService;

    @GetMapping("/{userId}")
    public Page<NotificationResponseDTO> lookup(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest =
                PageRequest.of(page - 1, size, Sort.by("requestAt").descending());
        return lookupService.lookup(userId, pageRequest);
    }
}
