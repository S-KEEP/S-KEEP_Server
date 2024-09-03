package Skeep.backend.weather.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.weather.dto.request.WeatherRequest;
import Skeep.backend.weather.dto.response.WeatherListDto;
import Skeep.backend.weather.service.WeatherLocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherLocationService weatherLocationService;

    @GetMapping
    public ResponseEntity<WeatherListDto> getWeatherList(
            @UserId Long userId,
            @RequestParam(value = "x") String x,
            @RequestParam(value = "y") String y,
            @RequestParam(value = "address") String address
    ) {
        List<WeatherListDto.WeatherDto> weatherList = weatherLocationService.getWeatherList(x, y, address);
        return ResponseEntity.ok(new WeatherListDto(weatherList));
    }
}
