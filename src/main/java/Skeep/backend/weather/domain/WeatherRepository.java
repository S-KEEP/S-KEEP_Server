package Skeep.backend.weather.domain;

import Skeep.backend.location.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findAllByLocation(Location location);
}
