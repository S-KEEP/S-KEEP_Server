package Skeep.backend.friend.domain;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Optional<Friend> findByToken(String token);
    Boolean existsByUser1AndUser2(User user1, User user2);
    void deleteByUser1AndUser2(User user1, User user2);
    void deleteByUser1(User user1);
    void deleteByUser2(User user2);
}
