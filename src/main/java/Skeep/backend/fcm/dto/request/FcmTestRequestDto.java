package Skeep.backend.fcm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FcmTestRequestDto(
        @JsonProperty("token")
        String token
) {
}
