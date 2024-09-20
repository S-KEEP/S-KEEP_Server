package Skeep.backend.friend.dto.response;

import Skeep.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FriendResponseDto(
        @JsonProperty(value = "id")
        Long id,
        @JsonProperty(value = "name")
        String name
) {
    public static FriendResponseDto of(
            final User user
    ) {
        return FriendResponseDto.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .build();
    }
}
