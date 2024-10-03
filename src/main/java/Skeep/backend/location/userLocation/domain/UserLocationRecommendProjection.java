package Skeep.backend.location.userLocation.domain;

import java.time.LocalDate;

public interface UserLocationRecommendProjection {
    Long getUserLocationId();
    String getLocationPlaceName();
    LocalDate getWeatherDate();
}
