package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.domain.UserLocationRecommendProjection;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import Skeep.backend.location.userLocation.exception.UserLocationErrorCode;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserLocationRetriever {
    private final UserLocationRepository userLocationRepository;

    public Page<UserLocation> findAllByUserIdAndUserCategory(
            Long userId,
            String category,
            Pageable pageable) {
        return userLocationRepository.findAllByUserIdAndUserCategory(userId, category, pageable);
    }

    public UserLocation findByUserAndId(
            User user,
            Long id) {
        return userLocationRepository.findByUserAndId(user, id)
                .orElseThrow(() -> BaseException.type(UserLocationErrorCode.MISMATCH_USER_AND_USER_LOCATION));
    }

    public Boolean existsByUserAndId(
            User user,
            Long id) {
        return userLocationRepository.existsByUserAndId(user, id);
    }

    public UserLocation findById(Long id) {
        return userLocationRepository.findById(id)
                .orElseThrow(() -> BaseException.type(UserLocationErrorCode.NOT_FOUND_USER_LOCATION));
    }

    public Optional<UserLocationRecommendProjection> findUserLocationRecommendByUserIdAndWeatherAndCreatedDate(
            User user
    ) {
        return userLocationRepository.findUserLocationRecommendByUserIdAndWeatherAndCreatedDate(user.getId());
    }

    public Long countByUserCategory(UserCategory userCategory) {
        return userLocationRepository.countByUserCategory(userCategory);
    }
}
