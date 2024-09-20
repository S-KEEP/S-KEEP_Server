package Skeep.backend.notification.service.CategoryNotification;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.notification.domain.CategoryNotification.CategoryNotification;
import Skeep.backend.notification.domain.CategoryNotification.CategoryNotificationRepository;
import Skeep.backend.notification.exception.CategoryNotificationErrorCode;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryNotificationRetriever {
    private final CategoryNotificationRepository categoryNotificationRepository;

    public List<CategoryNotification> getAllByUser(User user) {
        return categoryNotificationRepository.findAllByUser(user);
    }

    public CategoryNotification getByUserAndId(User user, Long id) {
        return categoryNotificationRepository.findByUserAndId(user, id)
                .orElseThrow(() -> BaseException.type(
                        CategoryNotificationErrorCode.CATEGORY_NOTIFICATION_NOT_FOUND)
                );
    }
}
