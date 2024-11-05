package eello.notification.repository;

import eello.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(
            value = "SELECT n FROM Notification n " +
                    "WHERE n.userId = :userId and " +
                    "DATEDIFF(NOW(), n.requestAt) <= 90"
    )
    Page<Notification> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
