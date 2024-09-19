package Skeep.backend.friend.service;

import Skeep.backend.friend.domain.Friend;
import Skeep.backend.friend.dto.response.FriendTokenResponseDto;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendSaver friendSaver;
    private final UserFindService userFindService;

    public FriendTokenResponseDto createFriendAndToken(Long userId) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        String token = UUID.randomUUID().toString();

        friendSaver.save(Friend.creatFriend(currentUser, token));

        return FriendTokenResponseDto.of(token);
    }
}
