package Skeep.backend.notification.service;

import Skeep.backend.notification.domain.CategoryNotification.CategoryNotificationRepository;
import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotificationRepository;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationRemover {
    private final CategoryNotificationRepository categoryNotificationRepository;
    private final UserLocationNotificationRepository userLocationNotificationRepository;

    @Transactional
    public void deleteByUser(User user) {
        categoryNotificationRepository.deleteByUser(user);
        userLocationNotificationRepository.deleteByUser(user);
    }
}
