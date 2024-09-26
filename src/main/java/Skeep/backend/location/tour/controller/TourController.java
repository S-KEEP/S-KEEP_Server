package Skeep.backend.location.tour.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.location.tour.dto.TourLocationDto;
import Skeep.backend.location.tour.dto.response.TourLocationList;
import Skeep.backend.location.tour.service.TourService;
import Skeep.backend.location.userLocation.domain.UserLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TourController {
    private final TourService tourService;

    @GetMapping("/tourism-org")
    public ResponseEntity<TourLocationList> getTourLocationList(
            @RequestParam(value = "x") String x,
            @RequestParam(value = "y") String y
    ) {
        TourLocationList tourLocationList = tourService.getLocationBasedList(Double.parseDouble(x), Double.parseDouble(y));
        return ResponseEntity.ok(tourLocationList);
    }

    @PostMapping("/user-location/tourism-org/{userCategoryId}")
    public ResponseEntity<Void> createUserLocationWithTourAPI(
            @UserId Long userId,
            @RequestBody @Valid TourLocationDto tourLocationDto,
            @PathVariable(value = "userCategoryId") Long userCategoryId
    ) {
        UserLocation userLocation = tourService.createUserLocationByTourAPI(userId, tourLocationDto, userCategoryId);
        return ResponseEntity.created(URI.create("/api/user-location/" + userLocation.getId())).build();
    }
}
