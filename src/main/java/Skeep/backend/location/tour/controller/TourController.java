package Skeep.backend.location.tour.controller;

import Skeep.backend.location.tour.dto.request.TourLocationRequest;
import Skeep.backend.location.tour.dto.response.TourLocationList;
import Skeep.backend.location.tour.service.TourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
