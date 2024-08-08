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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    }

    @Test
    void findAllById() {
        // given
        userCategoryRepository.save(UserCategoryFixture.EXCITING.toUserCategory(user));
        userCategoryRepository.save(UserCategoryFixture.PARK_NATURE.toUserCategory(user));
        userCategoryRepository.save(UserCategoryFixture.REST.toUserCategory(user));
        userCategoryRepository.save(UserCategoryFixture.HISTORY.toUserCategory(user));
        userCategoryRepository.save(UserCategoryFixture.CULTURE_FESTIVAL.toUserCategory(user));
        userCategoryRepository.save(UserCategoryFixture.SHOPPING_DOWNTOWN.toUserCategory(user));

        // when
        List<UserCategory> userCategoryList = userCategoryRepository.findAllByUserId(user.getId());

        // then
        assertAll(
                () -> assertThat(userCategoryList).hasSize(6),
                () -> assertThat(userCategoryList)
                        .extracting("name")
                        .containsExactlyInAnyOrder(
                                UserCategoryFixture.EXCITING.getName(),
                                UserCategoryFixture.PARK_NATURE.getName(),
                                UserCategoryFixture.REST.getName(),
                                UserCategoryFixture.HISTORY.getName(),
                                UserCategoryFixture.CULTURE_FESTIVAL.getName(),
                                UserCategoryFixture.SHOPPING_DOWNTOWN.getName()
                        ),
                () -> assertThat(userCategoryList)
                        .extracting("description")
                        .containsExactlyInAnyOrder(
                                UserCategoryFixture.EXCITING.getDescription(),
                                UserCategoryFixture.PARK_NATURE.getDescription(),
                                UserCategoryFixture.REST.getDescription(),
                                UserCategoryFixture.HISTORY.getDescription(),
                                UserCategoryFixture.CULTURE_FESTIVAL.getDescription(),
                                UserCategoryFixture.SHOPPING_DOWNTOWN.getDescription()
                        )
        );
    }
}