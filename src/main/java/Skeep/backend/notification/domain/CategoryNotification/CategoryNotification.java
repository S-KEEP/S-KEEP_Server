package Skeep.backend.notification.domain.CategoryNotification;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.notification.domain.BaseNotification.Notification;
import Skeep.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@SuperBuilder
@Table(name = "category_notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryNotification extends Notification {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_category_id")
    private UserCategory userCategory;

    public CategoryNotification createCategoryNotification(
            final UserCategory userCategory,
            final String title,
            final String type,
            final Boolean isChecked,
            final User user
    ) {
        return CategoryNotification.builder()
                .userCategory(userCategory)
                .title(title)
                .type(type)
                .isChecked(isChecked)
                .user(user)
                .build();
    }
}
