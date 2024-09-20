package Skeep.backend.friend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FriendConnectRequestDto(
        @JsonProperty(value = "token")
        String token
) {
}
