package Skeep.backend.user.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.dto.UserInformation;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserFindService userFindService;
    private final UserWithdrawalService userWithdrawalService;

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
}
