package Skeep.backend.user.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.dto.UserInformation;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
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
    private final UserService userService;
    private final UserFindService userFindService;

    @PostMapping
    public ResponseEntity<Void> withdrawalUser(@UserId Long userId) {
        userService.withdrawalUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserInformation> getUserInformation(@UserId Long userId) {
        User user = userFindService.findById(userId);
        return ResponseEntity.ok(UserInformation.toUserInformation(user));
    }
}
