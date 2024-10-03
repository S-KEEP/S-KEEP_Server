package Skeep.backend.notification.domain.UserLocationNotification;

import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.notification.domain.BaseNotification.Notification;
import Skeep.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@SuperBuilder
@Table(name = "users_location_notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocationNotification extends Notification {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_location_id")
    private UserLocation userLocation;

    public static UserLocationNotification createUserLocationNotification(
            final UserLocation userLocation,
            final String title,
            final String body,
            final String type,
            final Boolean isChecked,
            final User user
    ) {
        return UserLocationNotification.builder()
                                       .userLocation(userLocation)
                                       .title(title)
                                       .body(body)
                                       .type(type)
                                       .isChecked(isChecked)
                                       .user(user)
                                       .createdDate(LocalDateTime.now())
                                       .build();
    }
}
