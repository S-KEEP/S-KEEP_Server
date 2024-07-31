package Skeep.backend.location.location.service;

import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.domain.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Boolean existsByKakaoMapId(String kakaoMapId) {
        return locationRepository.existsByKakaoMapId(kakaoMapId);
    }

    public Location findByKakaoMapId(String kakaoMapId) {
        return locationRepository.findByKakaoMapId(kakaoMapId);
    }
}
