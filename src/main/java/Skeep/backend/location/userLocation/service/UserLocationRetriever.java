package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserLocationRetriever {

    private final UserLocationRepository userLocationRepository;

    public List<UserLocation> findAllByUserIdAndFixedCategory(Long userId, ECategory category) {
        return userLocationRepository.findAllByUserIdAndFixedCategory(userId, category.name());
    }
}
