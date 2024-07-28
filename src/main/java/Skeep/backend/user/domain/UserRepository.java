package Skeep.backend.user.domain;

import Skeep.backend.user.dto.UserSecurityForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // @Query
    @Query("select u.id as id from User u where u.appleSerialId = :appleSerialId")
    Optional<UserSecurityForm> findUserSecurityFromByAppleSerialId(@Param("appleSerialId") String appleSerialId);

    @Query("select u.id as id from User u where u.id = :id")
    Optional<UserSecurityForm> findUserSecurityFromById(@Param("id") Long id);

    // query method
    Optional<User> findByAppleSerialId(String appleSerialId);
    Optional<User> findById(Long id);
}