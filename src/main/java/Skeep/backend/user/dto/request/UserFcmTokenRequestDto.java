package Skeep.backend.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserFcmTokenRequestDto(
        @JsonProperty("token")
        String token
) {
}
