package Skeep.backend.category.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.exception.UserCategoryErrorCode;
import Skeep.backend.fixture.UserCategoryFixture;
import Skeep.backend.fixture.UserFixture;
import Skeep.backend.global.ServiceTest;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.userLocation.service.UserLocationRemover;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static Skeep.backend.fixture.UserCategoryFixture.*;
import static Skeep.backend.fixture.UserCategoryFixture.SHOPPING_DOWNTOWN;
import static Skeep.backend.fixture.UserFixture.*;
import static Skeep.backend.fixture.UserFixture.ALICE_JOHNSON;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DisplayName("[Service Layer] -> UserCategoryRemover")
class UserCategoryRemoverTest extends ServiceTest {
    @Autowired
    private UserCategoryRemover userCategoryRemover;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Test
    void 유저_카테고리_삭제시_유저와_카테고리가_일치하지_않으면_예외를_던진다() {
        // given
        User user1 = userRepository.save(ALICE_JOHNSON.toUser(EProvider.APPLE));
        User user2 = userRepository.save(JOHN_DOE.toUser(EProvider.APPLE));

        UserCategory userCategory1 = userCategoryRepository.save(ACTIVITY.toUserCategory(user1));
        UserCategory userCategory2 = userCategoryRepository.save(ACTIVITY.toUserCategory(user2));

        // when - then
        assertThatThrownBy(() -> userCategoryRemover.deleteUserCategory(user1.getId(), userCategory2.getId()))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(UserCategoryErrorCode.USER_CATEGORY_NOT_MATCHED.getMessage());
    }

    @Test
    void 유저_카테고리_삭제에_성공한다() {
        // given
        User user = userRepository.save(ALICE_JOHNSON.toUser(EProvider.APPLE));
        userCategoryRepository.save(ACTIVITY.toUserCategory(user));

        // when
        userCategoryRemover.deleteUserCategory(user.getId(), 1L);

        // then
        List<UserCategory> userCategoryList = userCategoryRepository.findAllByUserId(user.getId());
        assertThat(userCategoryList.size()).isEqualTo(0);
    }
}
