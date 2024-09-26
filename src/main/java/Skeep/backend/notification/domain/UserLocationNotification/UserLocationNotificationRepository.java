package Skeep.backend.notification.domain.UserLocationNotification;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLocationNotificationRepository extends JpaRepository<UserLocationNotification, Long> {
    List<UserLocationNotification> findAllByUser(User user);
    Optional<UserLocationNotification> findByUserAndId(User user, Long id);
    void deleteByUser(User user);
}
