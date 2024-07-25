package Skeep.backend.user.domain;

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
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apple_serial_id", nullable = false, unique = true)
    private String appleSerialId;

    @Column(name = "apple_refresh_token", unique = true)
    private String appleRefreshToken;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Builder
    public User(
            final String appleSerialId,
            final String name,
            final EProvider provider
    ) {
        this.appleSerialId = appleSerialId;
        this.name = name;
        this.provider = provider;
        this.status = EStatus.ACTIVATED;
    }
}
