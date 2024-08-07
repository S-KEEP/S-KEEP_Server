package Skeep.backend.location.userLocation.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.location.userLocation.dto.request.FixedCategoryDto;
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
            @RequestBody UserLocationGetDto UserLocationGetDto
    ) {
        return ResponseEntity.ok(
                userLocationService.getUserLocationListByUserCategory(userId, UserLocationGetDto)
        );
    }

    @PostMapping("/user-location")
    public ResponseEntity<?> createUserLocation(
            @UserId Long userId,
            @ModelAttribute ScreenshotUploadDto screenshotUploadDto
    ) {
        return ResponseEntity.created(
                   userLocationService.createUserLocation(userId, screenshotUploadDto)
               )
               .build();
    }
}
