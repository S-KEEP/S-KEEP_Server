package Skeep.backend.location.userLocation.dto.response;

import Skeep.backend.location.location.domain.Location;
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
            final Location location
    ) {
        return LocationDto.builder()
                          .id(location.getId())
                          .kakaoMapId(location.getKakaoMapId())
                          .x(location.getX())
                          .y(location.getY())
                          .fixedCategory(location.getFixedCategory().name())
                          .build();
    }
}
