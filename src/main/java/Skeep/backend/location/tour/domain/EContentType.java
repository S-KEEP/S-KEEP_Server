package Skeep.backend.location.tour.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EContentType {
    TOURISM_ATTRACTION(12, "관광지"),
    CULTURE_FACILITIES(14, "문화시설"),
    FESTIVAL_EVENT(15, "축제공연행사"),
    TRAVEL_COURSE(25, "여행코스"),
    LEISURE_SPORTS(28, "레포츠"),
    ACCOMMODATION(32, "숙박"),
    SHOPPING(38, "쇼핑"),
    RESTAURANT(39, "음식점"),
    NULL(-1, "");
    ;

    private final int id;
    private final String name;

    public static EContentType findById(int id) {
        for (EContentType eContentType : EContentType.values()) {
            if (eContentType.getId() == id) {
                return eContentType;
            }
        }
        return EContentType.NULL;
    }
}
