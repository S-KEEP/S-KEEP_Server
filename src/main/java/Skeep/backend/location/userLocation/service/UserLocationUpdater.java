package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.location.userLocation.domain.UserLocation;
import org.springframework.stereotype.Component;

@Component
public class UserLocationUpdater {

    public void updateUserCategory(
            UserLocation userLocation,
            UserCategory userCategory
    ) {
        userLocation.updateUserCategory(userCategory);
    }
}
