package Skeep.backend.fixture;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.location.location.domain.Location;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LocationFixture {
    SEOUL_TOWER("123456", "Seoul Tower", "105 Namsangongwon-gil, Yongsan-gu, Seoul", ECategory.REST, "126.988205", "37.551169"),
    GANGNAM_STATION("789101", "Gangnam Station", "Gangnam-daero, Gangnam-gu, Seoul", ECategory.PARK_NATURE, "127.027636", "37.497942"),
    HAN_RIVER_PARK("112131", "Han River Park", "330 Yeouidong-ro, Yeongdeungpo-gu, Seoul", ECategory.CULTURE_FESTIVAL, "126.998579", "37.528353"),
    MYEONGDONG_SHOPPING("415161", "Myeongdong Shopping", "Myeongdong-gil, Jung-gu, Seoul", ECategory.SHOPPING_DOWNTOWN, "126.985302", "37.563759"),
    NATIONAL_MUSEUM("718192", "National Museum", "137 Seobinggo-ro, Yongsan-gu, Seoul", ECategory.EXCITING, "126.979366", "37.523092");

    private final String kakaoMapId;
    private final String placeName;
    private final String roadAddress;
    private final ECategory fixedCategory;
    private final String x;
    private final String y;

    public Location toLocation() {
        return Location.builder()
                .kakaoMapId(kakaoMapId)
                .placeName(placeName)
                .roadAddress(roadAddress)
                .fixedCategory(fixedCategory)
                .x(x)
                .y(y)
                .build();
    }
}
