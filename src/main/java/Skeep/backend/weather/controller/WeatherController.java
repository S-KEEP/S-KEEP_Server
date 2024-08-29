package Skeep.backend.weather.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.weather.dto.request.WeatherRequest;
import Skeep.backend.weather.dto.response.WeatherListDto;
import Skeep.backend.weather.service.WeatherLocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherLocationService weatherLocationService;

    @GetMapping
    public ResponseEntity<WeatherListDto> getWeatherList(
            @UserId Long userId,
            @RequestBody @Valid WeatherRequest weatherRequest
    ) {
        List<WeatherListDto.WeatherDto> weatherList = weatherLocationService.getWeatherList(weatherRequest.x(), weatherRequest.y());
        return ResponseEntity.ok(new WeatherListDto(weatherList));
    }
}
