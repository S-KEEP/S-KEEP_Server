package Skeep.backend.category.domain;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findAllByUserId(Long userId);
    Optional<UserCategory> findByUserAndName(User user, String name);
    void deleteAllByUser(User user);
}
