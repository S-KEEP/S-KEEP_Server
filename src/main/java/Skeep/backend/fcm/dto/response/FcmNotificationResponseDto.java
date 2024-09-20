package Skeep.backend.fcm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FcmNotificationResponseDto(
        @JsonProperty(value = "type")
        String type,
        @JsonProperty(value = "url")
        String url
) {
        public static FcmNotificationResponseDto of(
                final String type,
                final String url
        ) {
                return FcmNotificationResponseDto.builder()
                                                 .type(type)
                                                 .url(url)
                                                 .build();
        }
}
