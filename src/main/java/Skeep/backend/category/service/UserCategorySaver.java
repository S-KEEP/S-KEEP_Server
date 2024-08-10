package Skeep.backend.category.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class UserCategorySaver {
    private final UserCategoryRepository userCategoryRepository;

    public void createUserCategoryList(User user) {
        Arrays.stream(ECategory.values())
                .forEach(eCategory -> UserCategory.createUserCategory(eCategory.getName(), eCategory.getDescription(), user));
    }

    public UserCategory saveUserCategory(UserCategory userCategory) {
        return userCategoryRepository.save(userCategory);
    }
}
