package Skeep.backend.notification.controller;


import Skeep.backend.global.annotation.UserId;
import Skeep.backend.notification.dto.request.NotificationCheckRequestDto;
import Skeep.backend.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getNotificationList(
            @UserId Long userId,
            @RequestParam(value = "page") int page
    ) {
        return ResponseEntity.ok(notificationService.getNotificationList(userId, page));
    }

    @PatchMapping("/check")
    public ResponseEntity<Void> checkNotification(
            @UserId Long userId,
            @RequestBody NotificationCheckRequestDto notificationCheckRequestDto
    ) {
        return ResponseEntity.ok(
                notificationService.checkNotification(userId, notificationCheckRequestDto)
        );
    }
}
