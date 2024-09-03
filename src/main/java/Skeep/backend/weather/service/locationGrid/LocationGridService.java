package Skeep.backend.weather.service.locationGrid;

import Skeep.backend.weather.domain.locationGrid.LocationGrid;
import Skeep.backend.weather.domain.locationGrid.LocationGridRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationGridService {
    private final LocationGridRepository locationGridRepository;

    public LocationGrid getLocationGridWithin3kmRadius(double longitude, double latitude) {
        List<LocationGrid> locationGridList = locationGridRepository.findAllWithin3kmRadius(longitude, latitude);
        if (locationGridList.isEmpty()) {
            return null;
        }
        return locationGridList.get(0);
    }
}
