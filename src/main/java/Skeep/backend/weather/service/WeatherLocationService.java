package Skeep.backend.weather.service;

import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.domain.LocationRepository;
import Skeep.backend.location.location.service.LocationRetriever;
import Skeep.backend.weather.domain.Weather;
import Skeep.backend.weather.domain.WeatherRepository;
import Skeep.backend.weather.dto.response.WeatherListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherLocationService {
    private final LocationRetriever locationRepository;
    private final WeatherSchedulerService weatherSchedulerService;
    private final WeatherRetriever weatherRetriever;

    public List<WeatherListDto.WeatherDto> getWeatherList(String x, String y) {
        Location location = locationRepository.findByXandY(x, y);

        List<Weather> weathers = new ArrayList<>();
        if (location == null) {
            weathers.addAll(weatherSchedulerService.analyzeShortTerm(null, x, y));
            weathers.addAll(weatherSchedulerService.analyzeMiddleTerm(null, x, y));
        } else{
            weathers = weatherRetriever.findAllByLocation(location);
        }

        return weathers.stream()
                .map(weather -> new WeatherListDto.WeatherDto(
                        weather.getDate(),
                        weather.getWeatherCondition(),
                        weather.getTemperature()
                ))
                .collect(Collectors.toList());
    }
}
