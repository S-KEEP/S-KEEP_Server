package Skeep.backend.location.userLocation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record UserLocationListDto(
        @JsonProperty("userLocationList")
        List<UserLocationDto> userLocationDtoList
) implements Serializable {
    public static UserLocationListDto of(
            final List<UserLocationDto> userLocationDtoList
    ) {
        return UserLocationListDto.builder()
                                  .userLocationDtoList(userLocationDtoList)
                                  .build();
    }
}
