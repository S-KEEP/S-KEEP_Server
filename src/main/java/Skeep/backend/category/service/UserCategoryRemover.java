package Skeep.backend.category.service;


import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.exception.UserCategoryErrorCode;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.userLocation.service.UserLocationRemover;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCategoryRemover {
    private final UserFindService userFindService;
    private final UserLocationRemover userLocationRemover;
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    public void deleteByUser(User user) {
        userCategoryRepository.deleteAllByUser(user);
    }

    @Transactional
    public void deleteByUserIdAndUserCategoryId(Long userId, Long userCategoryId) {
        User user = userFindService.findById(userId);
        UserCategory userCategory = userCategoryRetriever.findById(userCategoryId);

        if (!validateUserCategory(user, userCategory)) {
            throw BaseException.type(UserCategoryErrorCode.USER_CATEGORY_NOT_MATCHED);
        }

        userLocationRemover.deleteAllByUserAndUserCategory(user, userCategory);
        userCategoryRepository.deleteById(userCategoryId);
    }

    private Boolean validateUserCategory(User user, UserCategory userCategory) {
        return userCategory.getUser().getId() == user.getId();
    }
}
