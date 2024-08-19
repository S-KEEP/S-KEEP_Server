package Skeep.backend.location.location.domain;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kakao_map_id", nullable = false)
    private String kakaoMapId;

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "x", nullable = false)
    private String x;

    @Column(name = "y", nullable = false)
    private String y;

    @Column(name = "fixed_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ECategory fixedCategory;

    @Builder
    private Location(
            final String kakaoMapId,
            final String placeName,
            final String roadAddress,
            final ECategory fixedCategory,
            final String x,
            final String y
    ) {
        this.kakaoMapId = kakaoMapId;
        this.placeName = placeName;
        this.roadAddress = roadAddress;
        this.x = x;
        this.y = y;
        this.fixedCategory = fixedCategory;
    }

    public static Location createLocation(
            String kakaoMapId,
            String placeName,
            String roadAddress,
            ECategory fixedCategory
    ) {
        return Location.builder()
                .kakaoMapId(kakaoMapId)
                .placeName(placeName)
                .roadAddress(roadAddress)
                .fixedCategory(fixedCategory)
                .build();
    }
}
