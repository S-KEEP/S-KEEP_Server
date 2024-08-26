package Skeep.backend.location.tour.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.location.tour.dto.TourLocationDto;
import Skeep.backend.location.tour.dto.request.TourLocationRequest;
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
@RequestMapping("/api/user-location/tourism-org")
public class TourController {
    private final TourService tourService;

    @GetMapping("")
    public ResponseEntity<TourLocationList> getTourLocationList(
            @RequestBody @Valid TourLocationRequest request
    ) {
        TourLocationList tourLocationList = tourService.getLocationBasedList(Double.parseDouble(request.x()), Double.parseDouble(request.y()));
        return ResponseEntity.ok(tourLocationList);
    }

    @PostMapping("/tourism-org")
    public ResponseEntity<Void> createUserLocationWithTourAPI(
            @UserId Long userId,
            @RequestBody @Valid TourLocationDto tourLocationDto
    ) {
        UserLocation userLocation = tourService.createUserLocationByTourAPI(userId, tourLocationDto);
        return ResponseEntity.created(URI.create("/api/user-location/" + userLocation.getId())).build();
    }
}
