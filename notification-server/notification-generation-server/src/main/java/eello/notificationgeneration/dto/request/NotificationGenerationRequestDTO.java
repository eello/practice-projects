package eello.notificationgeneration.dto.request;

import eello.notificationgeneration.entity.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 알림 생성 요청 포맷
 */
@Getter
@Setter
@ToString
@Builder
public class NotificationGenerationRequestDTO {

    @NotNull
    private Long userId;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotNull
    private LocalDateTime requestAt;

    public Notification toEntity() {
        return Notification.builder()
                .userId(userId)
                .title(title)
                .body(body)
                .requestAt(requestAt)
                .build();
    }
}
