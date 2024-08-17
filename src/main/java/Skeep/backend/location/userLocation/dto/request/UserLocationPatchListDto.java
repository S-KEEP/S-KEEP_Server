package Skeep.backend.location.userLocation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserLocationPatchListDto(
        @JsonProperty("userLocationList")
        List<UserLocationImagePatchDto> userLocationImagePatchDtoList
) {
}
