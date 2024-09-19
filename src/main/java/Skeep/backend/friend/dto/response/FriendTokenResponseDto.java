package Skeep.backend.friend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FriendTokenResponseDto(
        @JsonProperty(value = "friendToken")
        String token
) {
    public static FriendTokenResponseDto of(String token) {
        return FriendTokenResponseDto.builder()
                                     .token(token)
                                     .build();
    }
}
