package Skeep.backend.location.picks.domain;

import Skeep.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PicksRepository extends JpaRepository<Picks, Long> {
}
