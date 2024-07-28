package Skeep.backend.location.domain;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Domain Test] UserLocation")
public class UserLocationTest {
    @Test
    void UserLocation_객체_생성에_성공하다() {
        User user = User.createAppleUser("1234567890", "Chaerin Yang");
        Location location = Location.builder()
                .kakaoMapId("kakaoMapIdTestId")
                .fixedCategory(ECategory.EXCITING)
                .build();
        UserCategory userCategory = UserCategory.builder()
                .name("대충 맛집")
                .description("진짜 대충대충 맛있는 음식이 있다.")
                .user(user)
                .build();
        UserLocation userLocation = UserLocation.builder()
                .user(user)
                .location(location)
                .userCategory(userCategory)
                .build();
        assertAll(
                () -> assertThat(userLocation.getUser()).isEqualTo(user),
                () -> assertThat(userLocation.getLocation()).isEqualTo(location),
                () -> assertThat(userLocation.getUserCategory()).isEqualTo(userCategory)
        );
    }
}
