package eello.notification.dto.response;

import eello.notification.entity.Notification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationCreateResponseDTO extends NotificationResponseDTO {

    private Boolean created;

    private NotificationCreateResponseDTO(Notification notification) {
        super(notification);
        this.created = notification.getId() != null;
    }

    public static NotificationCreateResponseDTO from(Notification notification) {
        return new NotificationCreateResponseDTO(notification);
    }
}
