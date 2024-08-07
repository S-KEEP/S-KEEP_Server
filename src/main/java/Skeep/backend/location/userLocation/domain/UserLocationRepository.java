package Skeep.backend.location.userLocation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    @Query(
            value = "SELECT * " +
                    "FROM users_location ul " +
                    "WHERE ul.user_id = :userId " +
                    "AND ul.location_id IN (" +
                    "SELECT l.id FROM location l " +
                    "WHERE l.fixed_category = :fixedCategory" +
                    ")",
            nativeQuery = true
    )
    List<UserLocation> findAllByUserIdAndFixedCategory(Long userId, String fixedCategory);
}
