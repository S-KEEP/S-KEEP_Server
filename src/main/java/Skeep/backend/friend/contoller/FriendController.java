package Skeep.backend.friend.contoller;

import Skeep.backend.friend.dto.request.FriendConnectRequestDto;
import Skeep.backend.friend.service.FriendService;
import Skeep.backend.global.annotation.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    @GetMapping
    public ResponseEntity<?> getFriendList(
            @UserId Long userId,
            @RequestParam(value = "page") int page
    ) {
        return ResponseEntity.ok(
                friendService.getFriendList(userId, page)
        );
    }

    @PatchMapping
    public ResponseEntity<Void> connectFriend(
            @UserId Long userId,
            @RequestBody FriendConnectRequestDto friendConnectRequestDto
    ) {
        return ResponseEntity.ok(
                friendService.connectFriend(userId, friendConnectRequestDto)
        );
    }

    @PostMapping("/invite")
    public ResponseEntity<?> getFriendToken(@UserId Long userId) {
        return ResponseEntity.ok(
                friendService.createFriendAndToken(userId)
        );
    }

    @DeleteMapping("/user/{targetId}")
    public ResponseEntity<Void> deleteFriend(
            @UserId Long userId,
            @PathVariable(value = "targetId") Long targetId
    ) {
        friendService.deleteFriend(userId, targetId);
        return ResponseEntity.noContent().build();
    }
}
