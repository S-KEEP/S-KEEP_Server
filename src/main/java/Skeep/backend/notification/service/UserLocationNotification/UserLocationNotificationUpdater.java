package Skeep.backend.notification.service.UserLocationNotification;

import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserLocationNotificationUpdater {

    @Transactional
    public void updateIsChecked(UserLocationNotification userLocationNotification) {
        userLocationNotification.updateIsChecked(Boolean.TRUE);
    }
}
