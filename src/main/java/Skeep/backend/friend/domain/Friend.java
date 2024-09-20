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
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Builder
    public Friend(final User user1, final String token) {
        this.user1 = user1;
        this.token = token;
    }

    public static Friend createFriend(final User user1, final String token) {
        return Friend.builder()
                     .user1(user1)
                     .token(token)
                     .build();
    }

    public void connectFriend(final User user2) {
        this.user2 = user2;
    }
}
