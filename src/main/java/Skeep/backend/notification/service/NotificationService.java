package Skeep.backend.notification.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.notification.domain.BaseNotification.NotificationProjection;
import Skeep.backend.notification.dto.request.NotificationCheckRequestDto;
import Skeep.backend.notification.dto.response.NotificationListResponseDto;
import Skeep.backend.notification.dto.response.NotificationResponseDto;
import Skeep.backend.notification.exception.NotificationErrorCode;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRetriever notificationRetriever;
    private final NotificationUpdater notificationUpdater;
    private final UserFindService userFindService;

    public NotificationListResponseDto getNotificationList(
            Long userId,
            int page
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        if (page < 1)
            throw BaseException.type(NotificationErrorCode.INVALID_PAGE_NOTIFICATION);

        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<NotificationProjection> notificationProjectionPage
                = notificationRetriever.getNotificationList(currentUser, pageable);

        if (page != 1 && page > notificationProjectionPage.getTotalPages())
            throw BaseException.type(NotificationErrorCode.INVALID_PAGE_NOTIFICATION);

        List<NotificationProjection> notificationList = notificationProjectionPage.getContent();

        List<NotificationResponseDto> notificationResponseDtoList
                = notificationList.stream()
                                  .map(NotificationResponseDto::of)
                                  .toList();

        return NotificationListResponseDto.of(
                notificationResponseDtoList,
                notificationProjectionPage.getTotalPages()
        );
    }

    public Void checkNotification(
            Long userId,
            NotificationCheckRequestDto notificationCheckRequestDto
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        Long id = notificationCheckRequestDto.id();
        String type = notificationCheckRequestDto.type();

        Object notification = notificationRetriever.getNotification(currentUser, id, type);
        notificationUpdater.updateNotification(notification);

        return null;
    }
}
