package Skeep.backend.location.picks.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PicksRepository extends JpaRepository<Picks, Long> {
    Optional<Picks> findByPlaceName(String placeName);
}
