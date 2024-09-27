package Skeep.backend.location.picks.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.location.picks.dto.request.PicksRequest;
import Skeep.backend.location.picks.dto.response.PicksDtoList;
import Skeep.backend.location.picks.service.PicksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @PostMapping("/user-category")
    public ResponseEntity<Void> addUserCategory(
            @UserId Long userId,
            @RequestParam("userCategoryId") Long userCategoryId,
            @RequestParam("title") String title) {
        Long userLocationId = picksService.addUserCategory(userId, userCategoryId, title);
        return ResponseEntity.created(URI.create("/api/user-location/" + userLocationId)).build();
    }
}
