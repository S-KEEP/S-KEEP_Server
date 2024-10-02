package Skeep.backend.fcm.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.fcm.constant.FcmConstants;
import Skeep.backend.fcm.dto.request.FcmTestRequestDto;
import Skeep.backend.fcm.dto.response.FcmNotificationResponseDto;
import Skeep.backend.fcm.exception.FcmErrorCode;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.service.UserLocationRetriever;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {
    private final UserFindService userFindService;
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserLocationRetriever userLocationRetriever;

    public Void testNotification(
            Long userId,
            FcmTestRequestDto fcmTestRequestDto
    ) {
        String token = fcmTestRequestDto.token();
        Boolean isCategory = fcmTestRequestDto.isCategory();
        String typeCategory = "category";
        String typeUserLocation = "userLocation";
        String url;
        String title;
        String body;
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        if (isCategory) {
            Long id = 594L;
            UserCategory userCategory = userCategoryRetriever.findById(id);
            title = currentUser.getName() + FcmConstants.CATEGORY_TITLE_1
                    + userCategory.getName() + FcmConstants.CATEGORY_TITLE_2;
            body = FcmConstants.CATEGORY_BODY;
            url = FcmConstants.CATEGORY_URL + userCategory.getId().toString();

            log.info("CATEGORY @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            log.info("title : {}", title);
            log.info("body : {}", body);
            log.info("url : {}", url);

            sendNotification(
                    Boolean.FALSE,
                    token,
                    title,
                    body,
                    userCategory.getId(),
                    typeCategory,
                    url
            );
        } else {
            Long id = 272L;
            UserLocation userLocation = userLocationRetriever.findById(id);
            title = currentUser.getName() + FcmConstants.USER_LOCATION_TITLE_1
                    + userLocation.getLocation().getPlaceName() + FcmConstants.USER_LOCATION_TITLE_2;
            body = "10월 8일" + FcmConstants.USER_LOCATION_BODY;
            url = FcmConstants.USER_LOCATION_URL + userLocation.getId().toString();

            log.info("USERLOCATION @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            log.info("title : {}", title);
            log.info("body : {}", body);
            log.info("url : {}", url);

            sendNotification(
                    Boolean.FALSE,
                    token,
                    title,
                    body,
                    userLocation.getId(),
                    typeUserLocation,
                    url
            );
        }

//        sendNotification(Boolean.FALSE, token, "테스트 >.0", "얏호~ 성공~", 6L, "category", "/category/205");

        return null;
    }

    @Async
    public void sendNotification(
            Boolean isScheduling,
            String token,
            String title,
            String body,
            Long id,
            String type,
            String url
    ) {
        Notification notification = Notification.builder()
                                                .setTitle(title)
                                                .setBody(body)
                                                .build();

        ApnsConfig apnsConfig
                = ApnsConfig.builder()
                            .setAps(Aps.builder()
                                       .setAlert(ApsAlert.builder()
                                                         .setTitle(title)
                                                         .setBody(body)
                                                         .build()
                                       )
                                       .setSound(FcmConstants.NOTIFICATION_DEFAULT_SOUND)
                                       .build()
                            )
                            .build();
        FcmNotificationResponseDto fcmNotificationResponseDto
                = FcmNotificationResponseDto.of(id, type, url);

        ObjectMapper objectMapper = new ObjectMapper();
        String fcmNotificationResponseJson;
        try {
            fcmNotificationResponseJson = objectMapper.writeValueAsString(fcmNotificationResponseDto);
        } catch (JsonProcessingException e) {
            log.info("fail to write fcm data");
            throw BaseException.type(FcmErrorCode.FCM_DATA_WRITE_FAIL);
        }

        Message message = Message.builder()
                                 .putData("data", fcmNotificationResponseJson)
                                 .setToken(token)
                                 .setNotification(notification)
                                 .setApnsConfig(apnsConfig)
                                 .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message: {}", response);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("fail to sent message");
            if (!isScheduling)
                throw BaseException.type(FcmErrorCode.FCM_FAIL);
        }
    }
}