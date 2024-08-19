package Skeep.backend.user.domain;

import Skeep.backend.user.dto.UserSecurityForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // @Query
    @Query("select u.id as id, u.role as role from User u where u.serialId = :serialId")
    Optional<UserSecurityForm> findUserSecurityFromBySerialId(@Param("serialId") String serialId);

    @Query("select u.id as id, u.role as role from User u where u.id = :id")
    Optional<UserSecurityForm> findUserSecurityFromById(@Param("id") Long id);

    @Query("select u from User u where u.modifiedDate < :date and u.status = :status")
    List<User> findUsersNotModifiedSince(@Param("date") LocalDateTime date, @Param("status") EStatus status);

    @Modifying
    @Transactional
    @Query("delete from User u where u.modifiedDate < :date and u.status = :status")
    void deleteUsersNotModifiedSince(@Param("date") LocalDateTime date, @Param("status") EStatus status);

    // query method
    Optional<User> findBySerialId(String serialId);
    Optional<User> findById(Long id);
    Optional<User> findByIdAndStatus(Long id, EStatus status);
}