package Skeep.backend.weather.domain.locationGrid;

import Skeep.backend.global.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DisplayName("[Repository Test] -> LocationGridRepository 테스트")
class LocationGridRepositoryTest extends RepositoryTest {
    @Autowired
    private LocationGridRepository locationGridRepository;

    @BeforeEach
    void setUp() {
        locationGridRepository.saveAll(List.of(
                LocationGrid.createLocationGrid("kor", "1100000000", "서울특별시", "", "", 60, 127,
                        126, 58, 48.03, 37, 33, 48.85, 126.980008333333, 37.5635694444444),
                LocationGrid.createLocationGrid("kor", "1111000000", "서울특별시", "종로구", "", 60, 127,
                        126, 58, 53.91, 37, 34, 13.36, 126.981641666666, 37.5703777777777),
                LocationGrid.createLocationGrid("kor", "1111051500", "서울특별시", "종로구", "청운효자동", 60, 127,
                        126, 58, 14.35, 37, 35, 2.89, 126.9706519, 37.5841367)
        ));
    }

    @Test
    void findAll() {
        List<LocationGrid> all = locationGridRepository.findAll();
        for (LocationGrid a : all) {
            System.out.println(a.getGridX() + " " + a.getGridY());
        }
    }
}