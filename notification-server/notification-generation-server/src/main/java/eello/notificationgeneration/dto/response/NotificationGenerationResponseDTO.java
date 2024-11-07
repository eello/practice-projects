package eello.notificationgeneration.dto.response;

import eello.notificationgeneration.entity.Notification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationGenerationResponseDTO extends NotificationResponseDTO {

    private Boolean created;

    private NotificationGenerationResponseDTO(Notification notification) {
        super(notification);
        this.created = notification.getId() != null;
    }

    public static NotificationGenerationResponseDTO from(Notification notification) {
        return new NotificationGenerationResponseDTO(notification);
    }
}
