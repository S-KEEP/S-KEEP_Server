package Skeep.backend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u.id as id from User u where u.serialId = :serialId")
    Optional<UserSecurityForm> findUserSecurityFromBySerialId(String serialId);

    @Query("select u.id as id from User u where u.id = :id")
    Optional<UserSecurityForm> findUserSecurityFromById(Long id);

    @Modifying(clearAutomatically = true)
    Optional<User> findById(Long userId);

    interface UserSecurityForm {
        Long getId();

        static UserSecurityForm invoke(User user){
            return new UserSecurityForm() {
                @Override
                public Long getId() {
                    return user.getId();
                }
            };
        }
    }
}