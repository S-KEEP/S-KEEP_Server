package Skeep.backend.fcm.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FcmNotificationResponseDto(
        @JsonProperty(value = "id")
        Long id,
        @JsonProperty(value = "type")
        String type,
        @JsonProperty(value = "url")
        String url
) {
        public static FcmNotificationResponseDto of(
                final Long id,
                final String type,
                final String url
        ) {
                return FcmNotificationResponseDto.builder()
                                                 .id(id)
                                                 .type(type)
                                                 .url(url)
                                                 .build();
        }
}
