package Skeep.backend.friend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record FriendListResponseDto(
        @JsonProperty(value = "friendList")
        List<FriendResponseDto> friendResponseDtoList,
        @JsonProperty(value = "totalPage")
        int totalPage
) {
    public static FriendListResponseDto of(
            final List<FriendResponseDto> friendResponseDtoList,
            final int totalPage
    ) {
        return FriendListResponseDto.builder()
                                    .friendResponseDtoList(friendResponseDtoList)
                                    .totalPage(totalPage)
                                    .build();
    }
}
