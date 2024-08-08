package Skeep.backend.location.userLocation.dto.response;

import Skeep.backend.category.domain.ECategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record LocationDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("kakaoMapId")
        String kakaoMapId,
        @JsonProperty("x")
        String x,
        @JsonProperty("y")
        String y,
        @JsonProperty("fixedCategory")
        String fixedCategory
) implements Serializable {
    public static LocationDto of(
            final Long id,
            final String kakaoMapId,
            final String x,
            final String y,
            final ECategory fixedCategory
    ) {
        return LocationDto.builder()
                          .id(id)
                          .kakaoMapId(kakaoMapId)
                          .x(x)
                          .y(y)
                          .fixedCategory(fixedCategory.getName())
                          .build();
    }
}
