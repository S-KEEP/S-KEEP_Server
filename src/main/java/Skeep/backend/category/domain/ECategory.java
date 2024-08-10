package Skeep.backend.category.domain;

import Skeep.backend.category.exception.CategoryErrorCode;
import Skeep.backend.global.exception.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ECategory {
    EXCITING("익사이팅", "Activities that are thrilling and exciting"),
    PARK_NATURE("공원/자연", "Parks and nature-related places"),
    REST("휴식", "Places ideal for rest and relaxation"),
    HISTORY("역사 및 유적지", "Historical sites and landmarks"),
    CULTURE_FESTIVAL("문화/축제", "Cultural events and festivals"),
    SHOPPING_DOWNTOWN("쇼핑/도심", "Shopping districts and downtown areas")
    ;

    private final String name;
    private final String description;

    public static ECategory findByName(String name) {
        return Arrays.stream(ECategory.values())
                .filter(eCategory -> eCategory.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> BaseException.type(CategoryErrorCode.INVALID_ENUM_CATEGORY));
    }
}
