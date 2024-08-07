package Skeep.backend.location.userLocation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserCategoryDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("title")
        String name,
        @JsonProperty("description")
        String description
) implements Serializable {
    public static UserCategoryDto of(
            final Long id,
            final String name,
            final String description
    ) {
        return UserCategoryDto.builder()
                              .id(id)
                              .name(name)
                              .description(description)
                              .build();
    }
}
