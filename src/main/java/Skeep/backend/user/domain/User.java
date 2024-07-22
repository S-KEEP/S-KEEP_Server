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

    @Column(name = "serial_id")
    private String serialId;

    @Column(name = "name")
    private String name;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Builder
    public User(String serialId, String name, EProvider provider, EStatus status) {
        this.serialId = serialId;
        this.name = name;
        this.provider = provider;
        this.status = status;
    }

}
