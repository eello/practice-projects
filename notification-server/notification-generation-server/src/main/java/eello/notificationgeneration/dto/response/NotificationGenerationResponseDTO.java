package eello.notificationgeneration.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationGenerationResponseDTO {

    private boolean accept;
    private Long requestId;

    public NotificationGenerationResponseDTO(boolean accept, Long requestId) {
        this.accept = accept;
        this.requestId = requestId;
    }

    public static NotificationGenerationResponseDTO of(Long requestId) {
        return new NotificationGenerationResponseDTO(true, requestId);
    }
}
