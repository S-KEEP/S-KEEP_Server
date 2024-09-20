package Skeep.backend.friend.service;

import Skeep.backend.friend.domain.Friend;
import Skeep.backend.friend.domain.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FriendSaver {
    private final FriendRepository friendRepository;

    @Transactional
    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }
}
