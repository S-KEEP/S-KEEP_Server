package Skeep.backend.notification.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryMostUserLocationProjection;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.fcm.constant.FcmConstants;
import Skeep.backend.fcm.service.FcmService;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.domain.UserLocationRecommendProjection;
import Skeep.backend.location.userLocation.service.UserLocationRetriever;
import Skeep.backend.notification.constant.NotificationConstants;
import Skeep.backend.notification.domain.CategoryNotification.CategoryNotification;
import Skeep.backend.notification.domain.UserLocationNotification.UserLocationNotification;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationScheduleService {
    private final FcmService fcmService;
    private final UserFindService userFindService;
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserLocationRetriever userLocationRetriever;
    private final NotificationSaver notificationSaver;

    @Async
    @Scheduled(cron = "0 0 10 * * *")
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

            UserCategory tempUserCategory
                    = UserCategory.createUserCategory(
                            userCategoryNameAndUserLocationCount.getUserCategoryId()
                    );
            CategoryNotification categoryNotification
                    = CategoryNotification.createCategoryNotification(
                            tempUserCategory,
                            title,
                            body,
                            NotificationConstants.CATEGORY_TYPE,
                            Boolean.FALSE,
                            user
                    );
            notificationSaver.saveNotification(categoryNotification);

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

    @Async
    @Scheduled(cron = "0 0 11 * * *")
    public void sendUserLocationNotification() {
        List<User> userList = userFindService.findAllByFcmTokenIsNotNull();

        log.info("userList : {}", userList);

        userList.forEach(user -> {

            Optional<UserLocationRecommendProjection> userLocationRecommendProjection
                    = userLocationRetriever.findUserLocationRecommendByUserIdAndWeatherAndCreatedDate(user);

            if (userLocationRecommendProjection.isEmpty())
                return;
            UserLocationRecommendProjection userLocationNameAndUserLocationCount
                    = userLocationRecommendProjection.get();

            String title = user.getName() + FcmConstants.USER_LOCATION_TITLE_1
                            + userLocationNameAndUserLocationCount.getLocationPlaceName()
                            + FcmConstants.USER_LOCATION_TITLE_2;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN);
            String formattedDate = userLocationNameAndUserLocationCount.getWeatherDate().format(formatter);
            String body = formattedDate + FcmConstants.USER_LOCATION_BODY;

            String url = FcmConstants.USER_LOCATION_URL
                            + userLocationNameAndUserLocationCount.getUserLocationId().toString();

            UserLocation tempUserLocation =
                    UserLocation.createUserLocation(
                            userLocationNameAndUserLocationCount.getUserLocationId()
                    );
            UserLocationNotification userLocationNotification
                    = UserLocationNotification.createUserLocationNotification(
                            tempUserLocation,
                            title,
                            body,
                            NotificationConstants.USER_LOCATION_TYPE,
                            Boolean.FALSE,
                            user
                    );
            notificationSaver.saveNotification(userLocationNotification);

            log.info("user(UserLocation) : {} fcm 호출", user.getName());
            fcmService.sendNotification(
                    Boolean.TRUE,
                    user.getFcmToken(),
                    title,
                    body,
                    userLocationNameAndUserLocationCount.getUserLocationId(),
                    "userLocation",
                    url
            );
            log.info("user(UserLocation) : {} fcm 끝", user.getName());
        });
    }
}
