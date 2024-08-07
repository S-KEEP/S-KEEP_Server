package Skeep.backend.location.userLocation.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import Skeep.backend.location.userLocation.exception.UserLocationErrorCode;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserLocationRetriever {

    private final UserLocationRepository userLocationRepository;

    public List<UserLocation> findAllByUserIdAndFixedCategory(Long userId, String category) {
        return userLocationRepository.findAllByUserIdAndFixedCategory(userId, category);
    }

    public UserLocation findByUserAndId(User user, Long id) {
        return userLocationRepository.findByUserAndId(user, id)
                .orElseThrow(() -> BaseException.type(UserLocationErrorCode.MISMATCH_USER_AND_USER_LOCATION));
    }
}
