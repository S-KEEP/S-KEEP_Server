package Skeep.backend.weather.domain.locationGrid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationGridRepository extends JpaRepository<LocationGrid, Long> {
    // @Query
    @Query(value = "SELECT lg, " +
            "    FUNCTION('ST_DISTANCE_SPHERE'," +
            "        FUNCTION('POINT', " +
            "            (lg.longitudeSecondPer100)," +
            "            (lg.latitudeSecondPer100)" +
            "        )," +
            "        FUNCTION('POINT', " +
            "            :searchLongitude, " +
            "            :searchLatitude" +
            "        )" +
            "    ) AS distance " +
            "FROM LocationGrid lg " +
            "WHERE " +
            "    FUNCTION('ST_DISTANCE_SPHERE'," +
            "        FUNCTION('POINT', " +
            "            (lg.longitudeSecondPer100)," +
            "            (lg.latitudeSecondPer100)" +
            "        )," +
            "        FUNCTION('POINT', " +
            "            :searchLongitude, " +
            "            :searchLatitude" +
            "        )" +
            "    ) <= 3000")
    List<LocationGrid> findAllWithin3kmRadius(@Param("searchLongitude") double searchLongitude, @Param("searchLatitude") double searchLatitude);
}
