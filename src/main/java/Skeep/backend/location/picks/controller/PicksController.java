package Skeep.backend.location.picks.controller;

import Skeep.backend.location.picks.dto.request.PicksRequest;
import Skeep.backend.location.picks.dto.response.PicksDtoList;
import Skeep.backend.location.picks.service.PicksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PicksDtoList> findAll() {
        return ResponseEntity.ok(picksService.findAll());
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteAll() {
        picksService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
