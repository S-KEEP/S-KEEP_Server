package Skeep.backend.weather.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.service.LocationRetriever;
import Skeep.backend.weather.domain.Weather;
import Skeep.backend.weather.domain.locationGrid.LocationGrid;
import Skeep.backend.weather.dto.response.WeatherListDto;
import Skeep.backend.weather.exception.WeatherErrorCode;
import Skeep.backend.weather.service.locationGrid.LocationGridService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static Skeep.backend.weather.util.RegionUtils.getRegionCode_land;
import static Skeep.backend.weather.util.RegionUtils.getRegionCode_ta;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherLocationService {
    private final LocationRetriever locationRepository;
    private final WeatherSchedulerService weatherSchedulerService;
    private final WeatherRetriever weatherRetriever;
    private final LocationGridService locationGridService;

    public List<WeatherListDto.WeatherDto> getWeatherList(String x, String y, String address) {
        Location location = locationRepository.findByXandY(x, y).orElse(null);
        List<Weather> weatherList = weatherRetriever.findAllByLocation(location);

        if (location == null || weatherList.size() == 0) {
            LocationGrid locationGrid = locationGridService.getLocationGridWithin3kmRadius(Double.parseDouble(x), Double.parseDouble(y));
            if (locationGrid == null) {
                throw BaseException.type(WeatherErrorCode.CANNOT_CONVERT_GRID);
            }

            weatherList.addAll(weatherSchedulerService.analyzeShortTerm(null, String.valueOf(locationGrid.getGridX()), String.valueOf(locationGrid.getGridY())));
            weatherList.addAll(weatherSchedulerService.analyzeMiddleTerm(null, getRegionCode_land(address), getRegionCode_ta(address)));
        }

        return weatherList.stream()
                .map(weather -> new WeatherListDto.WeatherDto(
                        weather.getDate(),
                        weather.getWeatherCondition(),
                        weather.getTemperature()
                ))
                .collect(Collectors.toList());
    }
}
