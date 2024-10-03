package Skeep.backend.notification.service;

import Skeep.backend.category.domain.UserCategoryMostUserLocationProjection;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.fcm.constant.FcmConstants;
import Skeep.backend.fcm.service.FcmService;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduleService {
    private final FcmService fcmService;
    private final UserFindService userFindService;
    private final UserCategoryRetriever userCategoryRetriever;

    @Async
    @Scheduled(cron = "0 0 9 * * *")
    public void sendCategoryNotification() {
        List<User> userList = userFindService.findAllByFcmTokenIsNotNull();

        log.info("userList : {}", userList);

        userList.forEach(user -> {
            Optional<UserCategoryMostUserLocationProjection> userCategoryWithMostUserLocation
                    = userCategoryRetriever.findTopCategoryWithMostUserLocations(user);
            if (userCategoryWithMostUserLocation.isEmpty())
                return;
            UserCategoryMostUserLocationProjection userCategoryNameAndUserLocationCount
                    = userCategoryWithMostUserLocation.get();

            if (userCategoryNameAndUserLocationCount.getUserLocationCount() <= 0)
                return;
            String userCategoryName = userCategoryNameAndUserLocationCount.getUserCategoryName();

            String title = user.getName() + FcmConstants.CATEGORY_TITLE_1
                    + userCategoryName + FcmConstants.CATEGORY_TITLE_2;
            String body = FcmConstants.CATEGORY_BODY;
            String url = FcmConstants.CATEGORY_URL
                    + userCategoryNameAndUserLocationCount.getUserCategoryId().toString();

            log.info("user(UserCategory) : {} fcm 호출", user.getName());
            fcmService.sendNotification(
                    Boolean.TRUE,
                    user.getFcmToken(),
                    title,
                    body,
                    userCategoryNameAndUserLocationCount.getUserCategoryId(),
                    "category",
                    url
            );
            log.info("user(UserCategory) : {} fcm 끝", user.getName());
        });
    }
}
