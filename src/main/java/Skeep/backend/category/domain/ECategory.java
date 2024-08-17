package Skeep.backend.category.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ECategory {
    REST("휴식", "방해받지 않고 싶을 때 가려고 모아둔 곳"),
    PARK_NATURE("공원/자연", "힐링하고 싶을 때"),
    CULTURE_FESTIVAL("문화/축제", "즐기고 싶은 곳"),
    SHOPPING_DOWNTOWN("쇼핑/도심", "가까운 곳을 가고 싶을 때"),
    EXCITING("익사이팅", "뛰어 놀고 싶을 때"),
    RESTAURANT("맛집", "여기는 꼭 먹어봐야 해"),
    HISTORY("역사 및 유적지", "언젠간 가볼 곳"),
    EXTRA("기타", "구분하기 애매한 카테고리")
    ;

    private final String name;
    private final String description;

    public static ECategory findByName(String name) {
        for(ECategory eCategory : ECategory.values())
            if(eCategory.getName().equals(name))
                return eCategory;
        return ECategory.EXTRA;
    }
}
