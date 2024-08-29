package Skeep.backend.weather.domain.locationGrid;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "location_grid")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationGrid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "division")
    private String division;

    @Column(name = "administrative_code")
    private String administrativeCode;

    @Column(name = "level1")
    private String level1;

    @Column(name = "level2")
    private String level2;

    @Column(name = "level3")
    private String level3;

    @Column(name = "grid_x")
    private int gridX;

    @Column(name = "grid_y")
    private int gridY;

    @Column(name = "longitude_degree")
    private int longitudeDegree;

    @Column(name = "longitude_minute")
    private int longitudeMinute;

    @Column(name = "longitude_second")
    private double longitudeSecond;

    @Column(name = "latitude_degree")
    private int latitudeDegree;

    @Column(name = "latitude_minute")
    private int latitudeMinute;

    @Column(name = "latitude_second")
    private double latitudeSecond;

    @Column(name = "longitude_second_per_100")
    private double longitudeSecondPer100;

    @Column(name = "latitude_second_per_100")
    private double latitudeSecondPer100;

    @Builder
    public LocationGrid(String division, String administrativeCode, String level1, String level2, String level3,
                        int gridX, int gridY, int longitudeDegree, int longitudeMinute, double longitudeSecond,
                        int latitudeDegree, int latitudeMinute, double latitudeSecond,
                        double longitudeSecondPer100, double latitudeSecondPer100) {
        this.division = division;
        this.administrativeCode = administrativeCode;
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.gridX = gridX;
        this.gridY = gridY;
        this.longitudeDegree = longitudeDegree;
        this.longitudeMinute = longitudeMinute;
        this.longitudeSecond = longitudeSecond;
        this.latitudeDegree = latitudeDegree;
        this.latitudeMinute = latitudeMinute;
        this.latitudeSecond = latitudeSecond;
        this.longitudeSecondPer100 = longitudeSecondPer100;
        this.latitudeSecondPer100 = latitudeSecondPer100;
    }

    public static LocationGrid createLocationGrid(String division, String administrativeCode, String level1, String level2, String level3,
                                                  int gridX, int gridY, int longitudeDegree, int longitudeMinute, double longitudeSecond,
                                                  int latitudeDegree, int latitudeMinute, double latitudeSecond,
                                                  double longitudeSecondPer100, double latitudeSecondPer100) {
        return LocationGrid.builder()
                .division(division)
                .administrativeCode(administrativeCode)
                .level1(level1)
                .level2(level2)
                .level3(level3)
                .gridX(gridX)
                .gridY(gridY)
                .longitudeDegree(longitudeDegree)
                .longitudeMinute(longitudeMinute)
                .longitudeSecond(longitudeSecond)
                .latitudeDegree(latitudeDegree)
                .latitudeMinute(latitudeMinute)
                .latitudeSecond(latitudeSecond)
                .longitudeSecondPer100(longitudeSecondPer100)
                .latitudeSecondPer100(latitudeSecondPer100)
                .build();
    }
}
