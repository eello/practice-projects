package eello.notificationgeneration.dto.response;

import eello.notificationgeneration.entity.Notification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
public class NotificationResponseDTO {

    private Long id;
    private Long userId;
    private String title;
    private String body;
    private String requestAt;

    protected NotificationResponseDTO(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUserId();
        this.title = notification.getTitle();
        this.body = notification.getBody();
        this.requestAt = formatRequestAt(notification.getRequestAt());
    }

    public static String formatRequestAt(LocalDateTime requestAt) {
        return DateTimeFormatter.ofPattern("MM월 dd일").format(requestAt);
    }

    public static NotificationResponseDTO from(Notification notification) {
        return new NotificationResponseDTO(notification);
    }

}
