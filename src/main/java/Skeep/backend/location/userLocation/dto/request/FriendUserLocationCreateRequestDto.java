package Skeep.backend.location.userLocation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FriendUserLocationCreateRequestDto(
        @JsonProperty(value = "targetLocationId")
        Long targetLocationId,
        @JsonProperty(value = "userCategoryId")
        Long userCategoryId
) {
}
