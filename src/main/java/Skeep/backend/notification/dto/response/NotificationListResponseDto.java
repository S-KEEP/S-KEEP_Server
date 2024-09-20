package Skeep.backend.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record NotificationListResponseDto(
        @JsonProperty(value = "notificationList")
        List<NotificationResponseDto> notificationResponseDtoList
) {
    public static NotificationListResponseDto of(
            List<NotificationResponseDto> notificationResponseDtoList
    ) {
        return NotificationListResponseDto.builder()
                                          .notificationResponseDtoList(notificationResponseDtoList)
                                          .build();
    }
}
