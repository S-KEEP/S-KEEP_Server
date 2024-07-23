package Skeep.backend.location.domain;

import Skeep.backend.category.domain.FixedCategory;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixed_category_id", nullable = false)
    private FixedCategory fixedCategory;

    @Builder
    public Location(final String kakaoMapId, final FixedCategory fixedCategory) {
        this.kakaoMapId = kakaoMapId;
        this.fixedCategory = fixedCategory;
    }
}
