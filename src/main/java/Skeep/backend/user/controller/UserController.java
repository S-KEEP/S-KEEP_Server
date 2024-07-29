package Skeep.backend.user.controller;

import Skeep.backend.global.annotation.UserId;
import Skeep.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> withdrawalUser(@UserId Long userId) {
        userService.withdrawalUser(userId);
        return ResponseEntity.ok().build();
    }
}
