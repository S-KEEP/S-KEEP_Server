package Skeep.backend.fcm.controller;

import Skeep.backend.fcm.dto.request.FcmTestRequestDto;
import Skeep.backend.fcm.service.FcmService;
import Skeep.backend.global.annotation.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FcmController {
    private final FcmService fcmService;

    @PostMapping("/fcm/test")
    public ResponseEntity<?> sendNotification(
            @UserId Long userId,
            @RequestBody FcmTestRequestDto fcmTestRequestDto
    ) {
        return ResponseEntity.ok(fcmService.testNotification(userId, fcmTestRequestDto));
    }
}
