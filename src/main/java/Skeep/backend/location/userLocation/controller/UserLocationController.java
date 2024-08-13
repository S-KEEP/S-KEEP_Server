package Skeep.backend.location.userLocation.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchDto;
import Skeep.backend.location.userLocation.dto.response.UserLocationCreate;
import Skeep.backend.location.userLocation.service.UserLocationService;
import Skeep.backend.screenshot.dto.request.ScreenshotUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserLocationController {

    private final UserLocationService userLocationService;

    @GetMapping("/user-location")
    public ResponseEntity<?> getUserLocationList(
            @UserId Long userId,
            @RequestParam String userCategory,
            @RequestParam int page
    ) {
        return ResponseEntity.ok(
                userLocationService.getUserLocationListByUserCategory(userId, userCategory, page)
        );
    }

    @GetMapping("/user-location/{userLocationId}")
    public ResponseEntity<?> getUserLocationRetrieve(
            @UserId Long userId,
            @PathVariable Long userLocationId
    ) {
        return ResponseEntity.ok(
                userLocationService.getUserLocationRetrieve(userId, userLocationId)
        );
    }

    @PostMapping("/user-location")
    public ResponseEntity<?> createUserLocation(
            @UserId Long userId,
            @ModelAttribute ScreenshotUploadDto screenshotUploadDto
    ) {
        UserLocationCreate userLocationCreate
                = userLocationService.createUserLocation(userId, screenshotUploadDto);

        return ResponseEntity.created(userLocationCreate.uri() )
                             .body(userLocationCreate.userLocationCreateDto());
    }

    @PatchMapping("/user-location/{userLocationId}")
    public ResponseEntity<?> updateUserLocation(
            @UserId Long userId,
            @PathVariable Long userLocationId,
            @RequestBody UserLocationPatchDto userLocationPatchDto
    ) {
        return ResponseEntity.ok(
                userLocationService.updateUserLocationWithUserCategory(
                        userId,
                        userLocationId,
                        userLocationPatchDto
                )
        );
    }

    @DeleteMapping("/user-location/{userLocationId}")
    public ResponseEntity<?> deleteUserLocation(
            @UserId Long userId,
            @PathVariable Long userLocationId
    ) {
        userLocationService.deleteUserLocation(userId, userLocationId);
        return ResponseEntity.noContent().build();
    }
}
