package Skeep.backend.category.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.dto.UserCategoryDto;
import Skeep.backend.category.exception.UserCategoryErrorCode;
import Skeep.backend.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCategoryUpdater {
    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    public void updateUserCategory(Long userId, UserCategoryDto userCategoryDto) {
        UserCategory userCategory = userCategoryRepository.findById(userCategoryDto.id()).orElseThrow(
                () -> BaseException.type(UserCategoryErrorCode.USER_CATEGORY_NOT_FOUND));

        if (!validateUserCategory(userId, userCategory)) {
            throw BaseException.type(UserCategoryErrorCode.USER_CATEGORY_NOT_MATCHED);
        }

        userCategory.updateUserCategory(userCategoryDto.name(), userCategoryDto.description());
    }

    private Boolean validateUserCategory(Long userId, UserCategory userCategory) {
        return userCategory.getUser().getId() == userId;
    }
}
