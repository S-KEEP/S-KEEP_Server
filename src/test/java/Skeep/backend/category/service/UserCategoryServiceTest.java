package Skeep.backend.category.service;

import Skeep.backend.category.dto.UserCategoryList;
import Skeep.backend.global.ServiceTest;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static Skeep.backend.fixture.UserCategoryFixture.*;
import static Skeep.backend.fixture.UserFixture.ALICE_JOHNSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Service Layer] -> UserCategoryService")
class UserCategoryServiceTest extends ServiceTest {
    @Autowired
    private UserCategoryService userCategoryService;

    @Nested
    @DisplayName("유저 카테고리 리스트 조회")
    class getUserCategoryList {
        private static User user;

        @BeforeEach
        void setUp() {
            user = userRepository.save(ALICE_JOHNSON.toUser(EProvider.APPLE));

            userCategoryRepository.save(ACTIVITY.toUserCategory(user));
            userCategoryRepository.save(PARK_NATURE.toUserCategory(user));
            userCategoryRepository.save(REST.toUserCategory(user));
            userCategoryRepository.save(HISTORY.toUserCategory(user));
            userCategoryRepository.save(CULTURE_FESTIVAL.toUserCategory(user));
            userCategoryRepository.save(SHOPPING_DOWNTOWN.toUserCategory(user));
        }

        @Test
        void 유저_카테고리_리스트_조회에_성공한다() {
            UserCategoryList userCategoryList = userCategoryService.getUserCategoryList(user.getId());

            assertAll(
                    () -> assertThat(userCategoryList).isNotNull(),
                    () -> assertThat(userCategoryList.userCategoryDtoList()).hasSize(6),
                    () -> assertThat(userCategoryList.userCategoryDtoList()).extracting("name")
                            .containsExactlyInAnyOrder(
                                    ACTIVITY.getName(),
                                    PARK_NATURE.getName(),
                                    REST.getName(),
                                    HISTORY.getName(),
                                    CULTURE_FESTIVAL.getName(),
                                    SHOPPING_DOWNTOWN.getName()
                            )
            );
        }
    }
}
