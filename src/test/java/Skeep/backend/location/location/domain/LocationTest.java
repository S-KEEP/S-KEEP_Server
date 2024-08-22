package Skeep.backend.location.location.domain;

import Skeep.backend.category.domain.ECategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Domain Test] Location")
public class LocationTest {
    @Test
    void LOCATION_객체_생성에_성공한다() {
        Location location = Location.builder()
                .kakaoMapId("kakaoMapIdTestId")
                .fixedCategory(ECategory.ACTIVITY)
                .build();

        assertAll(
                () -> assertThat(location.getKakaoMapId()).isEqualTo("kakaoMapIdTestId"),
                () -> assertThat(location.getFixedCategory()).isEqualTo(ECategory.ACTIVITY)
        );
    }
}
