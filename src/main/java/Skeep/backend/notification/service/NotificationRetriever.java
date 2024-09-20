package Skeep.backend.notification.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.notification.domain.BaseNotification.NotificationCustomRepositoryImpl;
import Skeep.backend.notification.domain.BaseNotification.NotificationProjection;
import Skeep.backend.notification.domain.CategoryNotification.CategoryNotification;
import Skeep.backend.notification.exception.NotificationErrorCode;
import Skeep.backend.notification.service.CategoryNotification.CategoryNotificationRetriever;
import Skeep.backend.notification.service.UserLocationNotification.UserLocationNotificationRetriever;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationRetriever {
    private final CategoryNotificationRetriever categoryNotificationRetriever;
    private final UserLocationNotificationRetriever userLocationNotificationRetriever;
    private final NotificationCustomRepositoryImpl notificationCustomRepositoryImpl;

    public Page<NotificationProjection> getNotificationList(
            User user,
            Pageable pageable
    ) {
        int limit = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        return notificationCustomRepositoryImpl.findAllByUserId(user.getId(), limit, offset, pageable);
    }

    public Object getNotification(
            User user,
            Long id,
            String type
    ) {
        Object notification;
        switch (type) {
            case "category" -> notification = categoryNotificationRetriever.getByUserAndId(user, id);
            case "userLocation" -> notification = userLocationNotificationRetriever.getByUserAndId(user, id);
            default -> throw BaseException.type(NotificationErrorCode.INVALID_NOTIFICATION_TYPE);
        }

        return notification;
    }
}
