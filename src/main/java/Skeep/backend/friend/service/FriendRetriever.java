package Skeep.backend.friend.service;

import Skeep.backend.friend.domain.Friend;
import Skeep.backend.friend.domain.FriendRepository;
import Skeep.backend.friend.exception.FriendErrorCode;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRetriever {
    private final FriendRepository friendRepository;

    public Friend getByToken(String token) {
        return friendRepository.findByToken(token)
                .orElseThrow(() -> BaseException.type(
                            FriendErrorCode.FRIEND_NOT_FOUND_WITH_TOKEN
                        )
                );
    }

    public Boolean existsByUser1AndUser2(User user1, User user2) {
        return friendRepository.existsByUser1AndUser2(user1, user2);
    }

    public Boolean existsByCrossUserCheck(User user1, User user2) {
        if (existsByUser1AndUser2(user1, user2) || existsByUser1AndUser2(user2, user1))
            return Boolean.TRUE;
        else
            throw BaseException.type(FriendErrorCode.FRIEND_NOT_FOUND);
    }
}
