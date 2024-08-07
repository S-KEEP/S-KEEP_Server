package Skeep.backend.location.userLocation.domain;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.global.base.BaseTimeEntity;
import Skeep.backend.location.location.domain.Location;
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
@Table(name = "users_location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_category_id", nullable = false)
    private UserCategory userCategory;

    @Builder
    public UserLocation(final User user) {
        this.user = user;
    }

    public void updateUserLocation(
            final String fileName,
            final Location location,
            final UserCategory userCategory
    ) {
        this.fileName = fileName;
        this.location = location;
        this.userCategory = userCategory;
    }
}
