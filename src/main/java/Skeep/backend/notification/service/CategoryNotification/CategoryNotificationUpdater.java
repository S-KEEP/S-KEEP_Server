package Skeep.backend.notification.service.CategoryNotification;

import Skeep.backend.notification.domain.CategoryNotification.CategoryNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CategoryNotificationUpdater {

    @Transactional
    public void updateIsCheck(CategoryNotification categoryNotification) {
        categoryNotification.updateIsChecked(Boolean.TRUE);
    }
}
