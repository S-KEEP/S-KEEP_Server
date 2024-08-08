package Skeep.backend.fixture;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserCategoryFixture {
    EXCITING("익사이팅", "Exciting activities"),
    PARK_NATURE("공원/자연", "Parks and nature-related activities"),
    REST("휴식", "Relaxation and rest"),
    HISTORY("역사 및 유적지", "Historical sites and landmarks"),
    CULTURE_FESTIVAL("문화/축제", "Cultural events and festivals"),
    SHOPPING_DOWNTOWN("쇼핑/도심", "Shopping and downtown areas"),
    ;

    private final String name;
    private final String description;

    public UserCategory toUserCategory(User user) {
        return UserCategory.builder()
                .name(name)
                .description(description)
                .user(user)
                .build();
    }
}