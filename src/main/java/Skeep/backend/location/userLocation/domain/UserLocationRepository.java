package Skeep.backend.location.userLocation.domain;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    Optional<UserLocation> findByUserAndId(User user, Long id);
    boolean existsByUserAndId(User user, Long id);
    void deleteByUserAndId(User user, Long id);

    @Query(
            value = "SELECT * " +
                    "FROM users_location ul " +
                    "WHERE ul.user_id = :userId " +
                    "AND ul.users_category_id IN (" +
                    "SELECT uc.id FROM users_category uc WHERE uc.name = :userCategoryName" +
                    ")",
            nativeQuery = true
    )
    List<UserLocation> findAllByUserIdAndFixedCategory(Long userId, String userCategoryName);
}
