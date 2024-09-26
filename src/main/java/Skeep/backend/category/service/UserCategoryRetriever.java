package Skeep.backend.category.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.dto.response.UserCategoryList;
import Skeep.backend.category.exception.UserCategoryErrorCode;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCategoryRetriever {
    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryList getUserCategoryList(Long userId) {
        List<UserCategory> userCategoryList = userCategoryRepository.findAllByUserId(userId);
        return UserCategoryList.createDto(userCategoryList);
    }

    public UserCategory findById(Long id) {
        return userCategoryRepository.findById(id)
                .orElseThrow(() -> BaseException.type(UserCategoryErrorCode.USER_CATEGORY_NOT_FOUND));
    }

    public UserCategory findByUserAndId(User user, Long id) {
        return userCategoryRepository.findByUserAndId(user, id)
                .orElseThrow(() -> BaseException.type(UserCategoryErrorCode.USER_CATEGORY_NOT_MATCHED));
    }

    public UserCategory findByUserAndName(User user,String name) {
        return userCategoryRepository.findByUserAndName(user, name)
                .orElseThrow(() -> BaseException.type(UserCategoryErrorCode.USER_CATEGORY_NOT_FOUND));
    }

    public List<UserCategory> findAllByUser(User user) {
        return userCategoryRepository.findAllByUserId(user.getId());
    }
}