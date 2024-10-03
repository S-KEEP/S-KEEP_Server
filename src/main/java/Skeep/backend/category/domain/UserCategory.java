package Skeep.backend.category.domain;

import Skeep.backend.global.base.BaseTimeEntity;
import Skeep.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@Table(name = "users_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserCategory(
            final Long id,
            final String name,
            final String description,
            final User user
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
    }

    public static UserCategory createUserCategory(
            final String name,
            final String description,
            final User user
    ) {
        return UserCategory.builder()
                           .name(name)
                           .description(description)
                           .user(user)
                           .build();
    }

    public static UserCategory createUserCategory(
            final Long id
    ) {
        return UserCategory.builder()
                           .id(id)
                           .build();
    }

    public void updateUserCategory(
            String name,
            String description
    ) {
        this.name = name;
        this.description = description;
    }
}
