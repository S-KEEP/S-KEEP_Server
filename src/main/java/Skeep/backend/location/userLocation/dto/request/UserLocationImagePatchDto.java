package Skeep.backend.location.userLocation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserLocationImagePatchDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("url")
        String imageUrl
) {
}
