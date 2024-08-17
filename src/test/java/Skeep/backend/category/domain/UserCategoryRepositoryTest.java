package Skeep.backend.category.domain;

import Skeep.backend.fixture.UserCategoryFixture;
import Skeep.backend.fixture.UserFixture;
import Skeep.backend.global.RepositoryTest;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Repository Test] -> UserCategoryRepository 테스트")
public class UserCategoryRepositoryTest extends RepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    private static User user;

    @BeforeEach
    public void setUp() {
        user = userRepository.save(UserFixture.ALICE_JOHNSON.toUser(EProvider.APPLE));

        List<UserCategoryFixture> fixtures = Arrays.asList(
                UserCategoryFixture.REST,
                UserCategoryFixture.PARK_NATURE,
                UserCategoryFixture.CULTURE_FESTIVAL,
                UserCategoryFixture.SHOPPING_DOWNTOWN,
                UserCategoryFixture.EXCITING,
                UserCategoryFixture.RESTAURANT,
                UserCategoryFixture.HISTORY,
                UserCategoryFixture.EXTRA
        );
        fixtures.forEach(fixture -> userCategoryRepository.save(fixture.toUserCategory(user)));
    }

    @Test
    void findAllById() {
        // when
        List<UserCategory> userCategoryList = userCategoryRepository.findAllByUserId(user.getId());

        // then
        assertAll(
                () -> assertThat(userCategoryList).hasSize(8),
                () -> assertThat(userCategoryList)
                        .extracting("name", "description")
                        .containsExactlyInAnyOrder(
                                tuple(UserCategoryFixture.REST.getName(), UserCategoryFixture.REST.getDescription()),
                                tuple(UserCategoryFixture.PARK_NATURE.getName(), UserCategoryFixture.PARK_NATURE.getDescription()),
                                tuple(UserCategoryFixture.CULTURE_FESTIVAL.getName(), UserCategoryFixture.CULTURE_FESTIVAL.getDescription()),
                                tuple(UserCategoryFixture.SHOPPING_DOWNTOWN.getName(), UserCategoryFixture.SHOPPING_DOWNTOWN.getDescription()),
                                tuple(UserCategoryFixture.EXCITING.getName(), UserCategoryFixture.EXCITING.getDescription()),
                                tuple(UserCategoryFixture.RESTAURANT.getName(), UserCategoryFixture.RESTAURANT.getDescription()),
                                tuple(UserCategoryFixture.HISTORY.getName(), UserCategoryFixture.HISTORY.getDescription()),
                                tuple(UserCategoryFixture.EXTRA.getName(), UserCategoryFixture.EXTRA.getDescription())
                        )
        );
    }

    @Test
    void findByUserAndName() {
        // when
        Optional<User> findUser = userRepository.findById(user.getId());
        Optional<UserCategory> findUserCategory = userCategoryRepository.findByUserAndName(findUser.get(), ECategory.HISTORY.getName());

        // then
        assertTrue(findUserCategory.isPresent());
        assertEquals(ECategory.HISTORY.getName(), findUserCategory.get().getName());
        assertEquals(user.getId(), findUserCategory.get().getUser().getId());
    }

    @Test
    void deleteAllByUser() {
        // when
        userCategoryRepository.deleteAllByUser(user);

        // then
        List<UserCategory> userCategories = userCategoryRepository.findAllByUserId(user.getId());
        assertTrue(userCategories.isEmpty());
    }
}