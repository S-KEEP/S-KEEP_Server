package Skeep.backend.notification.domain.CategoryNotification;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryNotificationRepository extends JpaRepository<CategoryNotification, Long> {
    // query method
    List<CategoryNotification> findAllByUser(User user);
    Optional<CategoryNotification> findByUserAndId(User user, Long id);

    // @Query
    @Transactional
    @Modifying
    @Query("DELETE FROM CategoryNotification cn WHERE cn.user = :user")
    void deleteByUser(@Param("user") User user);
}
