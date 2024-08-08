package Skeep.backend.category.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.dto.UserCategoryList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryList getUserCategoryList(Long userId) {
        List<UserCategory> userCategoryList = userCategoryRepository.findAllByUserId(userId);
        return UserCategoryList.createDto(userCategoryList);
    }
}