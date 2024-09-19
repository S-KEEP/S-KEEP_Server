package Skeep.backend.friend.domain;

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
@Table(name = "friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2_id;

    @Column(name = "token", nullable = false)
    private String token;

    @Builder
    public Friend(final User user1_id, final String token) {
        this.user1_id = user1_id;
        this.token = token;
    }

    public static Friend creatFriend(final User user1_id, final String token) {
        return Friend.builder()
                     .user1_id(user1_id)
                     .token(token)
                     .build();
    }
}
