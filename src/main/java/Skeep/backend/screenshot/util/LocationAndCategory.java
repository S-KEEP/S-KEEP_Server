package Skeep.backend.screenshot.util;

import Skeep.backend.category.domain.ECategory;
import lombok.Builder;

@Builder
public record LocationAndCategory(
    String locationName,
    ECategory category
) {
    public static LocationAndCategory of(
            String locationName,
            String category
    ) {
        return LocationAndCategory.builder()
                .locationName(locationName)
                .category(ECategory.findByName(category))
                .build();
    }
}
