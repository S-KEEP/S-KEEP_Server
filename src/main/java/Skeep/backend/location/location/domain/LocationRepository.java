package Skeep.backend.location.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByKakaoMapId(String kakaoMapId);
    Location findByKakaoMapId(String kakaoMapId);
}
