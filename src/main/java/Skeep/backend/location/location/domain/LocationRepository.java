package Skeep.backend.location.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByKakaoMapId(String kakaoMapId);
    Location findByKakaoMapId(String kakaoMapId);
    Optional<Location> findByXAndY(String x, String y);
}
