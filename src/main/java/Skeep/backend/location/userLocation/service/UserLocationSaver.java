package Skeep.backend.location.userLocation.service;

import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLocationSaver {
    private final UserLocationRepository userLocationRepository;

    public UserLocation createUserLocation(User user) {
        return userLocationRepository.save(
                UserLocation.builder()
                        .user(user)
                        .build());
    }

    public UserLocation createUserLocation(UserLocation userLocation) {
        return userLocationRepository.save(userLocation);
    }
}
