package Skeep.backend.category.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.fixture.UserFixture;
import Skeep.backend.global.ServiceTest;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Service Layer] -> UserCategorySaver")
class UserCategorySaverTest extends ServiceTest {
    @Autowired
    private UserCategorySaver userCategorySaver;

    @Test
    void 유저_카테고리를_디폴트로_생성하다() {
        // given
        User user = userRepository.save(UserFixture.BOB_BROWN.toUser(EProvider.APPLE));

        // when
        userCategorySaver.createUserCategoryList(user);

        // then
        Optional<User> find_user = userRepository.findById(user.getId());
        List<UserCategory> userCategories = userCategoryRepository.findAllByUserId(user.getId());

        assertAll(
                () -> assertThat(find_user.isPresent()).isTrue(),
                () -> assertThat(userCategories).hasSize(ECategory.values().length),
                () -> assertThat(userCategories).extracting("name")
                        .containsExactlyInAnyOrder(
                                Arrays.stream(ECategory.values()).map(ECategory::getName).toArray(String[]::new)
                        ),
                () -> assertThat(userCategories).extracting("description")
                        .containsExactlyInAnyOrder(
                                Arrays.stream(ECategory.values()).map(ECategory::getDescription).toArray(String[]::new)
                        ),
                () -> assertThat(userCategories).allMatch(category -> category.getUser().getId().equals(user.getId()))
        );
    }

    @Test
    void 사용자_카테고리를_저장하다() {
        // given
        User user = userRepository.save(UserFixture.BOB_BROWN.toUser(EProvider.APPLE));

        // when
        UserCategory savedUserCategory = userCategorySaver.saveUserCategory(user.getId(), "NEW_CATEGORY", "NEW_CATEGORY_DESCRIPTION");

        // then
        Optional<UserCategory> findUserCategory = userCategoryRepository.findById(savedUserCategory.getId());
        assertAll(
                () -> assertThat(findUserCategory.isPresent()).isTrue(),
                () -> assertThat(findUserCategory.get().getName()).isEqualTo("NEW_CATEGORY"),
                () -> assertThat(findUserCategory.get().getDescription()).isEqualTo("NEW_CATEGORY_DESCRIPTION"),
                () -> assertThat(findUserCategory.get().getUser()).isEqualTo(user)
        );
    }
}