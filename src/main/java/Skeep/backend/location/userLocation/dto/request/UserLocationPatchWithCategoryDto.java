package Skeep.backend.location.userLocation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserLocationPatchWithCategoryDto(
        @JsonProperty("userCategoryId")
        Long userCategoryId
) {
}
