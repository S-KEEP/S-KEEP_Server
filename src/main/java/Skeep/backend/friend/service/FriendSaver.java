package Skeep.backend.friend.service;

import Skeep.backend.friend.domain.Friend;
import Skeep.backend.friend.domain.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendSaver {
    private final FriendRepository friendRepository;

    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }
}
