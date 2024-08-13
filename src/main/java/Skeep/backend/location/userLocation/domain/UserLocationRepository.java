package Skeep.backend.location.userLocation.domain;

import Skeep.backend.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
            countQuery = "SELECT COUNT(*) " +
                         "FROM users_location ul " +
                         "WHERE ul.user_id = :userId " +
                         "AND ul.users_category_id IN (" +
                         "SELECT uc.id FROM users_category uc WHERE uc.name = :userCategoryName" +
                         ")",
            nativeQuery = true
    )
    Page<UserLocation> findAllByUserIdAndUserCategory(Long userId, String userCategoryName, Pageable pageable);
}
