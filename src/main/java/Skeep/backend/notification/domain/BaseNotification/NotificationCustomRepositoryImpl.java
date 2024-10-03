package Skeep.backend.notification.domain.BaseNotification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<NotificationProjection> findAllByUserId(Long userId, int limit, int offset, Pageable pageable) {
        String query = "(SELECT id, title, body, type, is_checked, created_date " +
                       "FROM users_location_notification " +
                       "WHERE user_id = :userId) " +
                       "UNION ALL " +
                       "(SELECT id, title, body, type, is_checked, created_date " +
                       "FROM category_notification " +
                       "WHERE user_id = :userId) " +
                       "ORDER BY created_date DESC " +
                       "LIMIT :limit OFFSET :offset";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("userId", userId);
        nativeQuery.setParameter("limit", pageable.getPageSize());
        nativeQuery.setParameter("offset", pageable.getOffset());

        List<Object[]> resultObjectList = nativeQuery.getResultList();

        List<NotificationProjection> notificationProjectionList = resultObjectList.stream()
                                                        .map(NotificationProjection::of)
                                                        .toList();

        String countQuery = "SELECT COUNT(*) FROM (" +
                            "(SELECT id FROM users_location_notification WHERE user_id = :userId) " +
                            "UNION ALL " +
                            "(SELECT id FROM category_notification WHERE user_id = :userId)" +
                            ") AS total";
        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        countNativeQuery.setParameter("userId", userId);

        int totalElements = ((Number) countNativeQuery.getSingleResult()).intValue();

        return new PageImpl<>(notificationProjectionList, pageable, totalElements);
    }
}
