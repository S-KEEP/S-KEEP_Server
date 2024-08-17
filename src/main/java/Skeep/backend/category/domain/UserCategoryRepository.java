package Skeep.backend.category.domain;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    // query method
    List<UserCategory> findAllByUserId(Long userId);
    Optional<UserCategory> findByUserAndName(User user, String name);

    // @Query
    @Modifying
    @Query("DELETE FROM UserCategory uc WHERE uc.user = :user")
    void deleteAllByUser(@Param(value = "user") User user);
}
