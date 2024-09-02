package Skeep.backend.location.location.service;

import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.domain.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocationRetriever {
    private final LocationRepository locationRepository;

    public Boolean existsByKakaoMapId(String kakaoMapId) {
        return locationRepository.existsByKakaoMapId(kakaoMapId);
    }

    public Location findByKakaoMapId(String kakaoMapId) {
        return locationRepository.findByKakaoMapId(kakaoMapId);
    }

    public Optional<Location> findByXandY(String x, String y) {
        return locationRepository.findByXAndY(x, y);
    }
}
