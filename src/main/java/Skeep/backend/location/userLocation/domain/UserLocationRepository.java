package Skeep.backend.location.userLocation.domain;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    // query method
    Optional<UserLocation> findByUserAndId(User user, Long id);
    boolean existsByUserAndId(User user, Long id);
    void deleteByUserAndId(User user, Long id);
    List<UserLocation> findAllByUser(User user);
    Long countByUserCategory(UserCategory userCategory);

    // @Query
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
    Page<UserLocation> findAllByUserIdAndUserCategory(
            @Param("userId") Long userId,
            @Param("userCategoryName") String userCategoryName,
            Pageable pageable
    );

    @Query(
            value = "SELECT ul.id as userLocationId, l.place_name as locationPlaceName, w.date as weatherDate " +
                    "FROM users_location ul " +
                    "JOIN location l ON ul.location_id = l.id " +
                    "JOIN weather w ON w.location_id = l.id " +
                    "WHERE ul.user_id = :userId " +
                    "AND ul.created_date >= CURRENT_DATE - INTERVAL 4 DAY " +
                    "AND w.date = (" +
                    "SELECT MIN(w2.date)" +
                    "FROM weather w2 " +
                    "WHERE w2.location_id = l.id " +
                    "AND w2.weather_condition NOT IN ('SNOW', 'RAIN', 'RAIN_AND_SNOW') " +
                    "AND w2.temperature < 35 " +
                    "AND w2.temperature > -12 " +
                    "AND w2.date > CURRENT_DATE" +
                    ")" +
                    "ORDER BY ul.created_date DESC " +
                    "LIMIT 1",
            nativeQuery = true
    )
    Optional<UserLocationRecommendProjection> findUserLocationRecommendByUserIdAndWeatherAndCreatedDate(Long userId);

    @Modifying
    @Query("DELETE FROM UserLocation ul WHERE ul.user = :user")
    void deleteAllByUser(@Param(value = "user") User user);

    @Modifying
    @Query("DELETE FROM UserLocation ul WHERE ul.user = :user and ul.userCategory = :userCategory")
    void deleteAllByUserAndUserCategory(@Param(value = "user") User user, @Param(value = "userCategory") UserCategory userCategory);
}
