package Skeep.backend.weather.dto.response;

import Skeep.backend.weather.domain.EWeatherCondition;

import java.time.LocalDate;
import java.util.List;

public record WeatherListDto(
        List<WeatherDto> weatherDtoList
) {
    public record WeatherDto(
            LocalDate date,
            EWeatherCondition eWeatherCondition,
            String temperature
    ) {
    }
}
