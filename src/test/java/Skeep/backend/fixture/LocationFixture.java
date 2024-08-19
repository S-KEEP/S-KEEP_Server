package Skeep.backend.fixture;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.location.location.domain.Location;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationFixture {
    SEOUL_TOWER("123456", ECategory.REST, "126.988205", "37.551169"),
    GANGNAM_STATION("789101", ECategory.PARK_NATURE, "127.027636", "37.497942"),
    HAN_RIVER_PARK("112131", ECategory.CULTURE_FESTIVAL, "126.998579", "37.528353"),
    MYEONGDONG_SHOPPING("415161", ECategory.SHOPPING_DOWNTOWN, "126.985302", "37.563759"),
    NATIONAL_MUSEUM("718192", ECategory.EXCITING, "126.979366", "37.523092");

    private final String kakaoMapId;
    private final ECategory fixedCategory;
    private final String x;
    private final String y;

    public Location toLocation() {
        return Location.builder()
                .kakaoMapId(kakaoMapId)
                .fixedCategory(fixedCategory)
                .x(x)
                .y(y)
                .build();
    }
}
