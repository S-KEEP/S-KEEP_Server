package Skeep.backend.notification.service.CategoryNotification;

import Skeep.backend.notification.domain.CategoryNotification.CategoryNotification;
import Skeep.backend.notification.domain.CategoryNotification.CategoryNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryNotificationSaver {
    private final CategoryNotificationRepository categoryNotificationRepository;

    public void save(CategoryNotification categoryNotification) {
        categoryNotificationRepository.save(categoryNotification);
    }
}
