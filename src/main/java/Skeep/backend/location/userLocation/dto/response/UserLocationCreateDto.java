package Skeep.backend.location.userLocation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record UserLocationCreateDto(
        @JsonProperty("userLocationList")
        List<UserLocationDto> userLocationDtoList
) implements Serializable {
    public static UserLocationCreateDto of(
            final List<UserLocationDto> userLocationDtoList
    ) {
        return UserLocationCreateDto.builder()
                .userLocationDtoList(userLocationDtoList)
                .build();
    }
}
