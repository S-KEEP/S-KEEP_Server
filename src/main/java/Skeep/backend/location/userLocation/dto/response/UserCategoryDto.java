package Skeep.backend.location.userLocation.dto.response;

import Skeep.backend.category.domain.UserCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Builder
public record UserCategoryDto(
        @JsonProperty("id")
        Long id,
        @JsonProperty("name")
        String name,
        @Nullable
        @JsonProperty("description")
        String description
) implements Serializable {
    public static UserCategoryDto of(
            final UserCategory userCategory
    ) {
        return UserCategoryDto.builder()
                              .id(userCategory.getId())
                              .name(userCategory.getName())
                              .description(userCategory.getDescription())
                              .build();
    }
}
