package Skeep.backend.user.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.dto.UserInformation;
import Skeep.backend.user.dto.request.UserFcmTokenRequestDto;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
import Skeep.backend.user.service.UserWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserFindService userFindService;
    private final UserWithdrawalService userWithdrawalService;
    private final UserService userService;

    @PostMapping("/withdrawal")
    public ResponseEntity<Void> withdrawalUser(@UserId Long userId) {
        userWithdrawalService.withdrawal(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserInformation> getUserInformation(@UserId Long userId) {
        User user = userFindService.findById(userId);
        return ResponseEntity.ok(UserInformation.toUserInformation(user));
    }

    @PatchMapping("/fcm-token")
    public ResponseEntity<?> sendNotification(
            @UserId Long userId,
            @RequestBody UserFcmTokenRequestDto userFcmTokenRequestDto
    ) {
        return ResponseEntity.ok(userService.initFcmToken(userId, userFcmTokenRequestDto));
    }
}
