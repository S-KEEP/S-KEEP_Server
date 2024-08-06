package Skeep.backend.location.userLocation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Builder
public record UserLocationDto(
        @JsonProperty("photoUrl")
        String photoUrl,
        @JsonProperty("location")
        LocationDto locationDto,
        @Nullable
        @JsonProperty("userCategory")
        UserCategoryDto userCategoryDto
) implements Serializable {
        public static UserLocationDto of(
                final String photoUrl,
                final LocationDto locationDto,
                final UserCategoryDto userCategoryDto
        ) {
                return UserLocationDto.builder()
                                      .photoUrl(photoUrl)
                                      .locationDto(locationDto)
                                      .userCategoryDto(userCategoryDto)
                                      .build();
        }
}
