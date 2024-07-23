package Skeep.backend.category.domain;

import Skeep.backend.category.exception.CategoryErrorCode;
import Skeep.backend.global.exception.BaseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ECategory {

    EXCITING("익사이팅"),
    PARK_NATURE("공원/자연"),
    REST("휴식"),
    HISTORY("역사 및 유적지"),
    CULTURE_FESTIVAL("문화/축제"),
    SHOPPING_DOWNTOWN("쇼핑/도심")
    ;

    private final String name;

    public static ECategory findByName(String name) {
        return Arrays.stream(ECategory.values())
                .filter(eCategory -> eCategory.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new BaseException(CategoryErrorCode.INVALID_ENUM_CATEGORY));
    }
}
