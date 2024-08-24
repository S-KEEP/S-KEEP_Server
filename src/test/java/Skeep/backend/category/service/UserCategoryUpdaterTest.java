package Skeep.backend.category.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.dto.UserCategoryDto;
import Skeep.backend.category.exception.UserCategoryErrorCode;
import Skeep.backend.global.ServiceTest;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static Skeep.backend.fixture.UserCategoryFixture.ACTIVITY;
import static Skeep.backend.fixture.UserFixture.ALICE_JOHNSON;
import static Skeep.backend.fixture.UserFixture.JOHN_DOE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("[Service Layer] -> UserCategoryUpdater")
class UserCategoryUpdaterTest extends ServiceTest {
    @Autowired
    private UserCategoryUpdater userCategoryUpdater;

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    @Test
    void 유저_카테고리_수정시_카테고리가_존재하지_않으면_예외를_던진다() {
        // given
        User user = userRepository.save(ALICE_JOHNSON.toUser(EProvider.APPLE));
        UserCategory userCategory = userCategoryRepository.save(ACTIVITY.toUserCategory(user));

        // when - then
        UserCategoryDto userCategoryDto = new UserCategoryDto(2L, "수정+액티비티", "수정+뛰어 놀고 싶을 때");
        assertThatThrownBy(() -> userCategoryUpdater.updateUserCategory(user.getId(), userCategoryDto))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(UserCategoryErrorCode.USER_CATEGORY_NOT_FOUND.getMessage());
    }

    @Test
    void 유저_카테고리_수정시_유저와_카테고리가_일치하지_않으면_예외를_던진다() {
        // given
        User user1 = userRepository.save(ALICE_JOHNSON.toUser(EProvider.APPLE));
        User user2 = userRepository.save(JOHN_DOE.toUser(EProvider.APPLE));

        UserCategory userCategory1 = userCategoryRepository.save(ACTIVITY.toUserCategory(user1));
        UserCategory userCategory2 = userCategoryRepository.save(ACTIVITY.toUserCategory(user2));

        // when - then
        UserCategoryDto userCategoryDto = new UserCategoryDto(2L, "수정+액티비티", "수정+뛰어 놀고 싶을 때");
        assertThatThrownBy(() -> userCategoryUpdater.updateUserCategory(user1.getId(), userCategoryDto))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining(UserCategoryErrorCode.USER_CATEGORY_NOT_MATCHED.getMessage());
    }

    @Test
    void 유저_카테고리_수정에_성공한다() {
        // given
        User user = userRepository.save(ALICE_JOHNSON.toUser(EProvider.APPLE));
        UserCategory userCategory = userCategoryRepository.save(ACTIVITY.toUserCategory(user));

        // when
        UserCategoryDto userCategoryDto = new UserCategoryDto(1L, "수정+액티비티", "수정+뛰어 놀고 싶을 때");
        userCategoryUpdater.updateUserCategory(user.getId(), userCategoryDto);

        // then
        Optional<UserCategory> findUserCategory = userCategoryRepository.findById(userCategory.getId());
        assertThat(findUserCategory.get().getName()).isEqualTo(userCategoryDto.name());
        assertThat(findUserCategory.get().getDescription()).isEqualTo(userCategoryDto.description());
    }
}
