package eello.notificationgeneration.dto.request;

import eello.notificationgeneration.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
public class NotificationGenerationRequestMessage {

    private Long requestId;
    private Long userId;
    private String title;
    private String body;
    private LocalDateTime requestAt;

    public static NotificationGenerationRequestMessage of(Long requestId, NotificationGenerationRequestDTO dto) {
        return NotificationGenerationRequestMessage.builder()
                .requestId(requestId)
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .requestAt(dto.getRequestAt())
                .build();
    }

    public Notification toEntity() {
        return Notification.builder()
                .userId(userId)
                .title(title)
                .body(body)
                .requestAt(requestAt)
                .build();
    }
}
