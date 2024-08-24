package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLocationRemover {
    private final UserLocationRepository userLocationRepository;

    public void deleteByUserIdAndId(User user, Long userLocationId) {
        userLocationRepository.deleteByUserAndId(user, userLocationId);
    }

    public void deleteAllByUserAndUserCategory(User user, UserCategory userCategory) {
        userLocationRepository.deleteAllByUserAndUserCategory(user, userCategory);
    }
}
