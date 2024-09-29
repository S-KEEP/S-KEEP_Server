package Skeep.backend.friend.service;

import Skeep.backend.friend.domain.FriendRepository;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FriendRemover {
    private final FriendRepository friendRepository;

    @Transactional
    public void deleteByUser1AndUser2(User user1, User user2) {
        friendRepository.deleteByUser1AndUser2(user1, user2);
    }

    @Transactional
    public void deleteByUser(User user) {
        friendRepository.deleteByUser1(user);
        friendRepository.deleteByUser2(user);
    }
}
