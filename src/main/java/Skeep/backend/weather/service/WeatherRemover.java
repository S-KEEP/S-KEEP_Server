package Skeep.backend.weather.service;

import Skeep.backend.weather.domain.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherRemover {
    private final WeatherRepository weatherRepository;

    public void deleteAll() {
        weatherRepository.deleteAll();
    }
}
