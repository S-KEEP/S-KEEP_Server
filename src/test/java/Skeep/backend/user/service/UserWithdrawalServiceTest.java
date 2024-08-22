package Skeep.backend.user.service;

import Skeep.backend.auth.jwt.domain.RefreshToken;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.fixture.LocationFixture;
import Skeep.backend.fixture.TokenFixture;
import Skeep.backend.fixture.UserCategoryFixture;
import Skeep.backend.fixture.UserFixture;
import Skeep.backend.global.ServiceTest;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Service Test] -> UserWithdrawalService 테스트")
public class UserWithdrawalServiceTest extends ServiceTest {
    @Autowired
    protected UserWithdrawalService userWithdrawalService;

    private static User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(UserFixture.ALICE_JOHNSON.toUser(EProvider.APPLE));

        List<UserCategoryFixture> fixtures = Arrays.asList(
                UserCategoryFixture.REST,
                UserCategoryFixture.PARK_NATURE,
                UserCategoryFixture.CULTURE_FESTIVAL,
                UserCategoryFixture.SHOPPING_DOWNTOWN,
                UserCategoryFixture.ACTIVITY,
                UserCategoryFixture.RESTAURANT,
                UserCategoryFixture.HISTORY,
                UserCategoryFixture.EXTRA
        );

        List<UserCategory> userCategoryList = fixtures.stream()
                .map(fixture -> fixture.toUserCategory(user))
                .map(userCategoryRepository::save)
                .collect(Collectors.toList());

        refreshTokenRepository.save(RefreshToken.issueRefreshToken(user.getId(), TokenFixture.REFRESH_TOKEN));

        List<Location> locations = List.of(
                LocationFixture.GANGNAM_STATION.toLocation(),
                LocationFixture.HAN_RIVER_PARK.toLocation(),
                LocationFixture.SEOUL_TOWER.toLocation()
        );
        List<String> imageNames = List.of("abc.jpg", "def.jpg", "ghi.jpg");
        for (int i = 0; i < locations.size(); i++) {
            Location location = locationRepository.save(locations.get(i));
            userLocationRepository.save(UserLocation.createUserLocation(imageNames.get(i), location, user, userCategoryList.get(0)));
        }
    }

    @Test
    void withdrawal() {
        // when
        userWithdrawalService.withdrawal(user.getId());

        // then
        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertTrue(deletedUser.isEmpty());

        // Verify that all user categories are deleted
        List<UserCategory> deletedUserCategories = userCategoryRepository.findAllByUserId(user.getId());
        assertTrue(deletedUserCategories.isEmpty());

        List<UserLocation> deletedUserLocations = userLocationRepository.findAllByUser(user);
        assertTrue(deletedUserLocations.isEmpty());
    }
}