package Skeep.backend.location.userLocation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FixedCategoryDto(
        @JsonProperty("fixedCategory")
        String fixedCategory
) {
}
