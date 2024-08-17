package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.location.location.domain.Location;
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

    public UserLocation updateUserLocation(
            UserLocation userLocation,
            Location location,
            UserCategory userCategory
    ) {
        userLocation.updateUserLocation(location, userCategory);
        return userLocation;
    }

    public UserLocation updateUserLocation(
            UserLocation userLocation,
            String fileName,
            Location location,
            UserCategory userCategory
    ) {
        userLocation.updateUserLocation(fileName, location, userCategory);
        return userLocation;
    }
}
