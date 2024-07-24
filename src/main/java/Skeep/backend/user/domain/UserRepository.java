package Skeep.backend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // @Query
    @Query("select u.id as id from User u where u.appleSerialId = :appleSerialId")
    Optional<UserSecurityForm> findUserSecurityFromByAppleSerialId(String appleSerialId);

    @Query("select u.id as id from User u where u.id = :id")
    Optional<UserSecurityForm> findUserSecurityFromById(Long id);

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

    // query method
    Optional<User> findByAppleSerialId(String appleSerialId);
}