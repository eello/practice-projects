package eello.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "noti")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String body;

    private LocalDateTime requestAt;

    @Builder
    public Notification(Long id, Long userId, String title, String body, LocalDateTime requestAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.requestAt = requestAt;
    }
}
