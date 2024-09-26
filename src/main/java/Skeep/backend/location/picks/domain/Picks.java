package Skeep.backend.location.picks.domain;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "picks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picks extends BaseTimeEntity {
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

    @Column(name = "fileName", nullable = false)
    private String fileName;

    @Column(name = "fixed_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ECategory fixedCategory;

    @Column(name = "editor_name", nullable = false)
    private String editorName;

    @Builder
    public Picks(
            final String kakaoMapId,
            final String placeName,
            final String roadAddress,
            final String x,
            final String y,
            final String fileName,
            final ECategory fixedCategory,
            final String editorName) {
        this.kakaoMapId = kakaoMapId;
        this.placeName = placeName;
        this.roadAddress = roadAddress;
        this.x = x;
        this.y = y;
        this.fileName = fileName;
        this.fixedCategory = fixedCategory;
        this.editorName = editorName;
    }

    public static Picks createPicks(
            String kakaoMapId,
            String placeName,
            String roadAddress,
            String x,
            String y,
            String fileName,
            ECategory fixedCategory,
            String editorName) {
        return Picks.builder()
                .kakaoMapId(kakaoMapId)
                .placeName(placeName)
                .roadAddress(roadAddress)
                .x(x)
                .y(y)
                .fileName(fileName)
                .fixedCategory(fixedCategory)
                .editorName(editorName)
                .build();
    }
}