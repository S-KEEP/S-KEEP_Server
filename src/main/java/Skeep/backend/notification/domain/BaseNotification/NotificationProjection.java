package Skeep.backend.notification.domain.BaseNotification;

import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
public record NotificationProjection(
        Long id,
        String title,
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
                .type((String) objects[2])
                .isChecked((Boolean) objects[3])
                .createdDate(((Timestamp) objects[4]).toLocalDateTime())
                .build();
    }
}
