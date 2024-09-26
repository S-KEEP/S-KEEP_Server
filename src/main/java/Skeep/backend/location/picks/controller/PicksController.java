package Skeep.backend.location.picks.controller;

import Skeep.backend.location.picks.dto.request.PicksRequest;
import Skeep.backend.location.picks.service.PicksService;
import Skeep.backend.location.tour.dto.TourLocationDto;
import Skeep.backend.location.tour.dto.response.TourLocationList;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/picks")
public class PicksController {
    private final PicksService picksService;

    @PostMapping("")
    public ResponseEntity<Void> updatePicks(@RequestBody @Valid PicksRequest request) {
        picksService.save(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<TourLocationList> findAll() {
        return ResponseEntity.ok(picksService.findAll());
    }
}
