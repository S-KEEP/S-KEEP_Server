package Skeep.backend.weather.service;

import Skeep.backend.weather.domain.Weather;
import Skeep.backend.weather.domain.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherSaver {
    private final WeatherRepository weatherRepository;

    public void save(List<Weather> weatherList) {
        weatherRepository.saveAll(weatherList);
    }
}
