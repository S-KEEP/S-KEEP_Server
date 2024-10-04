package Skeep.backend.notification.domain.UserLocationNotification;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserLocationNotificationRepository extends JpaRepository<UserLocationNotification, Long> {
    // query method
    List<UserLocationNotification> findAllByUser(User user);
    Optional<UserLocationNotification> findByUserAndId(User user, Long id);

    // @Query
    @Transactional
    @Modifying
    @Query("DELETE FROM UserLocationNotification uln WHERE uln.user = :user")
    void deleteByUser(@Param("user") User user);
}
