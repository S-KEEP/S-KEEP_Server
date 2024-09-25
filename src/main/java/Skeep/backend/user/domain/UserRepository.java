package Skeep.backend.user.domain;

import Skeep.backend.user.dto.UserSecurityForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(
            value = "SELECT u.* " +
                    "FROM friend f " +
                    "LEFT JOIN users u ON (f.user1_id = u.id AND f.user2_id IS NOT NULL AND f.user2_id = :userId) " +
                    "OR (f.user2_id = u.id AND f.user1_id = :userId) " +
                    "WHERE (f.user1_id = :userId OR f.user2_id = :userId)" +
                    "AND u.id IS NOT NULL",
            countQuery = "SELECT COUNT(*) " +
                         "FROM friend f " +
                         "LEFT JOIN users u ON (f.user1_id = u.id AND f.user2_id IS NOT NULL AND f.user2_id = :userId) " +
                         "OR (f.user2_id = u.id AND f.user1_id = :userId) " +
                         "WHERE (f.user1_id = :userId OR f.user2_id = :userId)" +
                         "AND u.id IS NOT NULL",
            nativeQuery = true
    )
    Page<User> findAllByUserInFriend(Long userId, Pageable pageable);

    @Query(
            value = "SELECT u.* " +
                    "FROM friend f " +
                    "LEFT JOIN users u ON (f.user1_id = u.id AND f.user2_id IS NOT NULL AND f.user2_id = :userId) " +
                    "OR (f.user2_id = u.id AND f.user1_id = :userId) " +
                    "WHERE (f.user1_id = :userId OR f.user2_id = :userId)" +
                    "AND u.id IS NOT NULL",
            nativeQuery = true
    )
    List<User> findWholeByUserInFriendWithNoPage(Long userId);

    @Modifying
    @Transactional
    @Query("delete from User u where u.modifiedDate < :date and u.status = :status")
    void deleteUsersNotModifiedSince(@Param("date") LocalDateTime date, @Param("status") EStatus status);

    // query method
    Optional<User> findBySerialId(String serialId);
    Optional<User> findById(Long id);
    Optional<User> findByIdAndStatus(Long id, EStatus status);
    List<User> findAllByFcmTokenIsNotNull();
}