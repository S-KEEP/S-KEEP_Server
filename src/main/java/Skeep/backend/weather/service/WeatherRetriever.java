package Skeep.backend.weather.service;

import Skeep.backend.location.location.domain.Location;
import Skeep.backend.weather.domain.Weather;
import Skeep.backend.weather.domain.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherRetriever {
    private final WeatherRepository weatherRepository;

    public List<Weather> findAll() {
        return weatherRepository.findAll();
    }

    public List<Weather> findAllByLocation(Location location) {
        return weatherRepository.findAllByLocation(location);
    }
}
