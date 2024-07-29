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

    @Column(name = "apple_serial_id", nullable = false, unique = true, updatable = false)
    private String appleSerialId;

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Embedded
    private Email email;

    @Column(name = "provider", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Builder
    private User(final String appleSerialId, final String name, final Email email, final EProvider provider) {
        this.appleSerialId = appleSerialId;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.status = EStatus.ACTIVATED;
    }

    public static User createAppleUser(final String appleSerialId, final String name, final Email email) {
        return User.builder()
                .appleSerialId(appleSerialId)
                .name(name)
                .email(email)
                .provider(EProvider.APPLE)
                .build();
    }

    public void updateStatus(final EStatus status) {
        this.status = status;
    }
}
