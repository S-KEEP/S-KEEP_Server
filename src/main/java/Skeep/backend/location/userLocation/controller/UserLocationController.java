package Skeep.backend.location.userLocation.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchListDto;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchWithCategoryDto;
import Skeep.backend.location.userLocation.dto.response.UserLocationCreate;
import Skeep.backend.location.userLocation.service.UserLocationService;
import Skeep.backend.screenshot.dto.request.ScreenshotUploadDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-location")
public class UserLocationController {

    private final UserLocationService userLocationService;

    @GetMapping
    public ResponseEntity<?> getUserLocationList(
            @UserId Long userId,
            @RequestParam String userCategory,
            @RequestParam int page
    ) {
        return ResponseEntity.ok(
                userLocationService.getUserLocationListByUserCategory(userId, userCategory, page)
        );
    }

    @GetMapping("/{userLocationId}")
    public ResponseEntity<?> getUserLocationRetrieve(
            @UserId Long userId,
            @PathVariable Long userLocationId
    ) {
        return ResponseEntity.ok(
                userLocationService.getUserLocationRetrieve(userId, userLocationId)
        );
    }

    @PostMapping
    public ResponseEntity<?> createUserLocation(
            @UserId Long userId,
            @ModelAttribute ScreenshotUploadDto screenshotUploadDto
    ) {
        UserLocationCreate userLocationCreate
                = userLocationService.createUserLocation(userId, screenshotUploadDto);

        return ResponseEntity.created(userLocationCreate.uri())
                             .body(userLocationCreate.userLocationCreateDto());
    }

    @PatchMapping("/{userLocationId}/category")
    public ResponseEntity<?> updateUserLocationWithCategory(
            @UserId Long userId,
            @PathVariable Long userLocationId,
            @RequestBody UserLocationPatchWithCategoryDto userLocationPatchWithCategoryDto
    ) {
        return ResponseEntity.ok(
                userLocationService.updateUserLocationWithUserCategory(
                        userId,
                        userLocationId,
                        userLocationPatchWithCategoryDto
                )
        );
    }

    @PatchMapping("/reanalysis")
    public ResponseEntity<?> updateUserLocation(
            @UserId Long userId,
            @RequestBody UserLocationPatchListDto userLocationPatchListDto
    ) {
        return ResponseEntity.ok(
                userLocationService.updateUserLocation(
                        userId,
                        userLocationPatchListDto
                )
        );
    }

    @DeleteMapping("/{userLocationId}")
    public ResponseEntity<?> deleteUserLocation(
            @UserId Long userId,
            @PathVariable Long userLocationId
    ) {
        userLocationService.deleteUserLocation(userId, userLocationId);
        return ResponseEntity.noContent().build();
    }
}
