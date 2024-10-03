package Skeep.backend.notification.service;

import Skeep.backend.notification.domain.CategoryNotification.CategoryNotification;
import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotification;
import Skeep.backend.notification.service.CategoryNotification.CategoryNotificationSaver;
import Skeep.backend.notification.service.UserLocationNotification.UserLocationNotificationSaver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationSaver {
    private final UserLocationNotificationSaver userLocationNotificationSaver;
    private final CategoryNotificationSaver categoryNotificationSaver;

    @Transactional
    public void saveNotification(Object notification) {
        if (notification instanceof UserLocationNotification)
            userLocationNotificationSaver.save((UserLocationNotification) notification);
        else if (notification instanceof CategoryNotification)
            categoryNotificationSaver.save((CategoryNotification) notification);
    }
}
