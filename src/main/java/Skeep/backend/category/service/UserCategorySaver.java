package Skeep.backend.category.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserCategorySaver {
    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    public void createUserCategoryList(User user) {
        Arrays.stream(ECategory.values())
                .forEach(eCategory -> {
                    UserCategory userCategory = UserCategory.createUserCategory(eCategory.getName(), eCategory.getDescription(), user);
                    userCategoryRepository.save(userCategory);
                });
    }

    @Transactional
    public UserCategory saveUserCategory(UserCategory userCategory) {
        return userCategoryRepository.save(userCategory);
    }
}
