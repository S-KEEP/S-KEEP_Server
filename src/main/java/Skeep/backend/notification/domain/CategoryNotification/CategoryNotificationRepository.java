package Skeep.backend.notification.domain.CategoryNotification;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryNotificationRepository extends JpaRepository<CategoryNotification, Long> {
    List<CategoryNotification> findAllByUser(User user);
    Optional<CategoryNotification> findByUserAndId(User user, Long id);
}
