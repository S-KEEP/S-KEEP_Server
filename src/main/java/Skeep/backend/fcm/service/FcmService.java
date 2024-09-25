package Skeep.backend.fcm.service;

import Skeep.backend.fcm.constant.FcmContants;
import Skeep.backend.fcm.dto.request.FcmTestRequestDto;
import Skeep.backend.fcm.dto.response.FcmNotificationResponseDto;
import Skeep.backend.fcm.exception.FcmErrorCode;
import Skeep.backend.global.exception.BaseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FcmService {
    public Void testNotification(FcmTestRequestDto fcmTestRequestDto) {

        String token = fcmTestRequestDto.token();

        sendNotification(Boolean.FALSE, token, "테스트 >.0", "얏호~ 성공~", 6L, "category", "/category/205");

        return null;
    }

    private void sendNotification(
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