package Skeep.backend.weather.domain;

import Skeep.backend.location.location.domain.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private LocalDate date;

    @Column(name = "weatherCondition")
    @Enumerated(EnumType.STRING)
    private EWeatherCondition weatherCondition;

    private String temperature;

    @Builder
    public Weather(
            Location location,
            LocalDate date,
            EWeatherCondition weatherCondition,
            String temperatures
    ) {
        this.location = location;
        this.date = date;
        this.weatherCondition = weatherCondition;
        this.temperature = temperatures;
    }

    public static Weather createWeather(
            Location location,
            LocalDate date,
            EWeatherCondition weatherCondition,
            String temperatures
    ) {
        return Weather.builder()
                .location(location)
                .date(date)
                .weatherCondition(weatherCondition)
                .temperatures(temperatures)
                .build();
    }
}
