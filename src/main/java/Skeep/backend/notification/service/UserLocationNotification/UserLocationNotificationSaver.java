package Skeep.backend.notification.service.UserLocationNotification;

import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotification;
import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLocationNotificationSaver {
    private final UserLocationNotificationRepository userLocationNotificationRepository;

    public void save(UserLocationNotification userLocationNotification) {
        userLocationNotificationRepository.save(userLocationNotification);
    }
}
