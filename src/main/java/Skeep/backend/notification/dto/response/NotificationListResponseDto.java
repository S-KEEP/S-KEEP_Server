package Skeep.backend.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record NotificationListResponseDto(
        @JsonProperty(value = "notificationList")
        List<NotificationResponseDto> notificationResponseDtoList,
        @JsonProperty(value = "totalPage")
        int totalPage
) {
    public static NotificationListResponseDto of(
            final List<NotificationResponseDto> notificationResponseDtoList,
            final int totalPage
    ) {
        return NotificationListResponseDto.builder()
                                          .notificationResponseDtoList(notificationResponseDtoList)
                                          .totalPage(totalPage)
                                          .build();
    }
}
