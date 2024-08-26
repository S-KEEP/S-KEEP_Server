package Skeep.backend.location.userLocation.domain;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.fixture.UserFixture;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Domain Test] UserLocation")
public class UserLocationTest {
    @Test
    void UserLocation_객체_생성에_성공하다() {
        User user = UserFixture.BOB_BROWN.toUser(EProvider.APPLE);
        Location location = Location.createLocation(
                "kakaoMapIdTestId",
                "옹근달 성수점",
                "서울 성동구 성수이로7길 41-1",
                "126.9854701651",
                "37.5743256886",
                ECategory.ACTIVITY
        );
        UserCategory userCategory = UserCategory.createUserCategory("맛집", "진짜 맛있는 집만 모아놓은 카테고리", user);
        UserLocation userLocation = UserLocation.createUserLocation("abc.jpg", location, user, userCategory);

        assertAll(
                () -> assertThat(userLocation.getUser()).isEqualTo(user),
                () -> assertThat(userLocation.getLocation()).isEqualTo(location),
                () -> assertThat(userLocation.getUserCategory()).isEqualTo(userCategory)
        );
    }
}