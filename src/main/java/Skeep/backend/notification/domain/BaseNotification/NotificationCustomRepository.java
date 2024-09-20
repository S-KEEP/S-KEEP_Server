package Skeep.backend.notification.domain.BaseNotification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationCustomRepository {
    Page<NotificationProjection> findAllByUserId(
            Long userId,
            int limit,
            int offset,
            Pageable pageable
    );
}
