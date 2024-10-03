package Skeep.backend.notification.domain.BaseNotification;

import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
public record NotificationProjection(
        Long id,
        String title,
        String body,
        String type,
        Boolean isChecked,
        LocalDateTime createdDate
) {
    public static NotificationProjection of(
            final Object[] objects
    ) {
        return NotificationProjection.builder()
                .id(((Number) objects[0]).longValue())
                .title((String) objects[1])
                .body((String) objects[2])
                .type((String) objects[3])
                .isChecked((Boolean) objects[4])
                .createdDate(((Timestamp) objects[5]).toLocalDateTime())
                .build();
    }
}
