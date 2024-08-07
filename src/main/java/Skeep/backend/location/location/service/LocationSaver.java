package Skeep.backend.location.location.service;

import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.domain.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationSaver {

    private final LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }
}
