package Skeep.backend.friend.service;

import Skeep.backend.friend.domain.Friend;
import Skeep.backend.friend.dto.request.FriendConnectRequestDto;
import Skeep.backend.friend.dto.response.FriendTokenResponseDto;
import Skeep.backend.friend.exception.FriendErrorCode;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRetriever friendRetriever;
    private final FriendSaver friendSaver;
    private final FriendUpdater friendUpdater;
    private final UserFindService userFindService;

    public FriendTokenResponseDto createFriendAndToken(Long userId) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        String token = UUID.randomUUID().toString();

        friendSaver.save(Friend.createFriend(currentUser, token));

        return FriendTokenResponseDto.of(token);
    }

    public Void connectFriend(Long userId, FriendConnectRequestDto friendConnectRequestDto) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        String token = friendConnectRequestDto.token();

        Friend currentFriend = friendRetriever.getByToken(token);
        User masterFriendUser = currentFriend.getUser1();
        User slaveFriendUser = currentFriend.getUser2();

        // 자기 자신 체크
        if (masterFriendUser.equals(currentUser))
            throw BaseException.type(FriendErrorCode.FRIEND_WITH_THEMSELVES);

        // 이미 존재하는 경우
        if (friendRetriever.existsByUser1AndUser2(masterFriendUser, currentUser)
                || friendRetriever.existsByUser1AndUser2(currentUser, masterFriendUser))
            throw BaseException.type(FriendErrorCode.ALREADY_CONNECTED);
        if (friendRetriever.existsByUser1AndUser2(currentUser, masterFriendUser))
            throw BaseException.type(FriendErrorCode.ALREADY_CONNECTED);

        // 누군가 이미 친구 추가를 함. 토큰을 누군가 썼을 경우.
        if (slaveFriendUser != null)
            throw BaseException.type(FriendErrorCode.EXPIRED_TOKEN);

        friendUpdater.connectFriend(currentFriend, currentUser);

        return null;
    }
}
