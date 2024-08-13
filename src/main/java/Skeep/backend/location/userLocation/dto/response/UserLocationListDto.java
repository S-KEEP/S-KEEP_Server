package Skeep.backend.location.userLocation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record UserLocationListDto(
        @JsonProperty("userLocationList")
        List<UserLocationDto> userLocationDtoList,
        @JsonProperty("totalPage")
        int totalPage
) implements Serializable {
    public static UserLocationListDto of(
            final List<UserLocationDto> userLocationDtoList,
            final int totalPage
    ) {
        return UserLocationListDto.builder()
                                  .userLocationDtoList(userLocationDtoList)
                                  .totalPage(totalPage)
                                  .build();
    }
}
