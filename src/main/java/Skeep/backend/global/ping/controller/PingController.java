package Skeep.backend.global.ping.controller;

import Skeep.backend.auth.apple.service.AppleService;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.location.location.domain.LocationRepository;
import Skeep.backend.location.location.service.LocationRetriever;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.weather.domain.locationGrid.LocationGrid;
import Skeep.backend.weather.domain.locationGrid.LocationGridRepository;
import Skeep.backend.weather.service.WeatherSchedulerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ping")
public class PingController {
    @Value("${test.email}")
    private String testEmail;

    private final UserFindService userFindService;
    private final AppleService appleService;
    private final LocationGridRepository locationGridRepository;
    private final WeatherSchedulerService weatherSchedulerService;
    private final LocationRepository locationRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody @Valid String serialId) {
        User user = userFindService.findUserBySerialId(serialId);
        JwtDto jwtDto = appleService.createJwtDto(user.getId(), user.getRole());
        return ResponseEntity.ok(jwtDto);
    }

    @PostMapping("/login/test")
    public ResponseEntity<JwtDto> login() {
//        throw BaseException.type(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        User user = userFindService.findUserByEmail(testEmail);
        JwtDto jwtDto = appleService.createJwtDto(user.getId(), user.getRole());
        return ResponseEntity.ok(jwtDto);
    }

    @GetMapping("/location-grid")
    public ResponseEntity<?> getLocationGrid() {
        List<LocationGrid> locationGrids = locationGridRepository.findAllWithin3kmRadius(126.980008333333, 37.5635694444444);
        return ResponseEntity.ok(locationGrids);
    }

    @GetMapping("/weather")
    public ResponseEntity<?> getWeather() {
        weatherSchedulerService.updateWeather();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/location-list")
    public ResponseEntity<?> getLocationList() {
        return ResponseEntity.ok(locationRepository.findAll());
    }
}
