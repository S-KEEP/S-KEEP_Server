package Skeep.backend.location.userLocation.domain;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.fixture.LocationFixture;
import Skeep.backend.fixture.UserCategoryFixture;
import Skeep.backend.fixture.UserFixture;
import Skeep.backend.global.RepositoryTest;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.domain.LocationRepository;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Repository Test] -> UserLocationRepository 테스트")
class UserLocationRepositoryTest extends RepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserLocationRepository userLocationRepository;

    private static User user;
    private List<UserCategory> userCategoryList;

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
        fixtures.forEach(fixture -> userCategoryRepository.save(fixture.toUserCategory(user)));

        userCategoryList = fixtures.stream()
                .map(fixture -> fixture.toUserCategory(user))
                .map(userCategoryRepository::save)
                .collect(Collectors.toList());
    }

    @Test
    void deleteAllByUser() {
        // given
        Location gangnamStation = locationRepository.save(LocationFixture.GANGNAM_STATION.toLocation());
        Location hanRiverPark = locationRepository.save(LocationFixture.HAN_RIVER_PARK.toLocation());
        Location seoulTower = locationRepository.save(LocationFixture.SEOUL_TOWER.toLocation());

        userLocationRepository.save(UserLocation.createUserLocation("abc.jpg", gangnamStation, user, userCategoryList.get(0)));
        userLocationRepository.save(UserLocation.createUserLocation("def.jpg", hanRiverPark, user, userCategoryList.get(0)));
        userLocationRepository.save(UserLocation.createUserLocation("ghi.jpg", seoulTower, user, userCategoryList.get(0)));

        // when
        userLocationRepository.deleteAllByUser(user);

        // then
        List<UserLocation> remainingUserLocations = userLocationRepository.findAllByUser(user);

        assertAll(
                () -> assertThat(remainingUserLocations).isEmpty(),
                () -> assertThat(userLocationRepository.count()).isZero()
        );
    }
}