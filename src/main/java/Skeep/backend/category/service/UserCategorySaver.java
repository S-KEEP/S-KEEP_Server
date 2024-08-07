package Skeep.backend.category.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCategorySaver {

    private final UserCategoryRepository userCategoryRepository;

    public UserCategory saveUserCategory(UserCategory userCategory) {
        return userCategoryRepository.save(userCategory);
    }

}
