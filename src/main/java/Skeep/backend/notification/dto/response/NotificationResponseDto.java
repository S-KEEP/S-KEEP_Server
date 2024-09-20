package Skeep.backend.notification.dto.response;

import Skeep.backend.notification.domain.BaseNotification.NotificationProjection;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponseDto(
        @JsonProperty(value = "id")
        Long id,
        @JsonProperty(value = "title")
        String title,
        @JsonProperty(value = "body")
        String body,
        @JsonProperty(value = "type")
        String type,
        @JsonProperty(value = "isChecked")
        Boolean isChecked,
        @JsonProperty(value = "createdAt")
        LocalDateTime createdDate
) {
    public static NotificationResponseDto of(
            NotificationProjection notification
    ) {
        return NotificationResponseDto.builder()
                .id(notification.id())
                .title(notification.title())
                .body("야호")
                .type(notification.type())
                .isChecked(notification.isChecked())
                .createdDate(notification.createdDate())
                .build();
    }
}