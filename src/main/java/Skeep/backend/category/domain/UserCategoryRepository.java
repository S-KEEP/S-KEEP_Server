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
    Optional<UserCategory> findByUserAndId(User user, Long id);
    void deleteById(Long id);

    // @Query
    @Modifying
    @Query("DELETE FROM UserCategory uc WHERE uc.user = :user")
    void deleteAllByUser(@Param(value = "user") User user);

    @Query(
            value = "SELECT uc.id as userCategoryId, uc.name as userCategoryName, COUNT(ul.id) as userLocationCount " +
                    "FROM users_category uc " +
                    "LEFT JOIN users_location ul ON ul.users_category_id = uc.id " +
                    "WHERE uc.user_id = :userId " +
                    "GROUP BY uc.id, uc.name " +
                    "ORDER BY userLocationCount DESC " +
                    "LIMIT 1",
            nativeQuery = true
    )
    Optional<UserCategoryMostUserLocationProjection> findTopCategoryWithMostUserLocations(Long userId);
}
