package Skeep.backend.category.domain;

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
@Table(name = "fixed_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedCategory extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ECategory category;

    @Builder
    public FixedCategory(ECategory category) {
        this.category = category;
    }
}
