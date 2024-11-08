package eello.notificationgeneration.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RequestInfo {

    private Long userId;
    private String title;
    private String body;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime requestAt;

    private Boolean result;

    @Builder
    public RequestInfo(Long userId, String title, String body, LocalDateTime requestAt) {
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.requestAt = requestAt;
        this.result = false;
    }

    public void success() {
        this.result = true;
    }
}
