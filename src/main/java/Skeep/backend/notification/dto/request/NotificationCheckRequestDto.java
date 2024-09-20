package Skeep.backend.notification.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationCheckRequestDto(
        @JsonProperty(value = "id")
        Long id,
        @JsonProperty(value = "type")
        String type
) {
}
