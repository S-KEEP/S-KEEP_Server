package Skeep.backend.friend.contoller;

import Skeep.backend.friend.service.FriendService;
import Skeep.backend.global.annotation.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/invite")
    public ResponseEntity<?> getFriendToken(@UserId Long userId) {
        return ResponseEntity.ok(
                friendService.createFriendAndToken(userId)
        );
    }
}
