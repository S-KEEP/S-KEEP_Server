package Skeep.backend.notification.service;

import Skeep.backend.notification.domain.CategoryNotification.CategoryNotification;
import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotification;
import Skeep.backend.notification.service.CategoryNotification.CategoryNotificationUpdater;
import Skeep.backend.notification.service.UserLocationNotification.UserLocationNotificationUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationUpdater {
    private final CategoryNotificationUpdater categoryNotificationUpdater;
    private final UserLocationNotificationUpdater userLocationNotificationUpdater;

    public void updateNotification(Object notification) {
        if (notification instanceof CategoryNotification)
            categoryNotificationUpdater.updateIsCheck((CategoryNotification) notification);
        else if (notification instanceof UserLocationNotification)
            userLocationNotificationUpdater.updateIsChecked((UserLocationNotification) notification);
    }
}
