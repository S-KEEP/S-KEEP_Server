package Skeep.backend.location.userLocation.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchListDto;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchWithCategoryDto;
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
            @RequestParam(value = "userCategoryId") Long userCategoryId,
            @RequestParam(value = "page") int page
    ) {
        return ResponseEntity.ok(
                userLocationService.getUserLocationListByUserCategory(userId, userCategoryId, page)
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

        return ResponseEntity.created(userLocationCreate.uri())
                             .body(userLocationCreate.userLocationCreateDto());
    }

    @PatchMapping("/user-location/{userLocationId}/category")
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

    @PatchMapping("/user-location/reanalysis")
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

    @DeleteMapping("/user-location/{userLocationId}")
    public ResponseEntity<?> deleteUserLocation(
            @UserId Long userId,
            @PathVariable Long userLocationId
    ) {
        userLocationService.deleteUserLocation(userId, userLocationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{targetId}/user-category/{userCategoryId}/user-location")
    public ResponseEntity<?> getFriendUserLocationListWithUserCategory(
            @UserId Long userId,
            @PathVariable(value = "targetId") Long targetId,
            @PathVariable(value = "userCategoryId") Long userCategoryId,
            @RequestParam(value = "page") int page
    ) {
        return ResponseEntity.ok(
                userLocationService.getFriendUserLocationListWithUserCategory(
                        userId,
                        targetId,
                        userCategoryId,
                        page
                )
        );
    }
}
