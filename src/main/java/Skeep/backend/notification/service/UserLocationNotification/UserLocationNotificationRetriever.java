package Skeep.backend.notification.service.UserLocationNotification;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotification;
import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotificationRepository;
import Skeep.backend.notification.exception.UserLocationNotificationErrorCode;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserLocationNotificationRetriever {
    private final UserLocationNotificationRepository userLocationNotificationRepository;

    public List<UserLocationNotification> getAllByUser(User user) {
        return userLocationNotificationRepository.findAllByUser(user);
    }

    public UserLocationNotification getByUserAndId(User user, Long id) {
        return userLocationNotificationRepository.findByUserAndId(user, id)
                .orElseThrow(() -> BaseException.type(
                        UserLocationNotificationErrorCode.USER_LOCATION_NOTIFICATION_NOT_FOUND
                ));
    }
}
