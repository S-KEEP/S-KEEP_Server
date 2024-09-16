package Skeep.backend.fcm.service;

import Skeep.backend.fcm.constant.FcmContants;
import Skeep.backend.fcm.dto.request.FcmTestRequestDto;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FcmService {
    public Void testNotification(FcmTestRequestDto fcmTestRequestDto) {

        String token = fcmTestRequestDto.token();

        sendNotification(Boolean.FALSE, token, "테스트 >.0", "얏호~ 성공~");

        return null;
    }

    private void sendNotification(Boolean isScheduling, String token, String title, String body) {
        Notification notification = Notification.builder()
                                                .setTitle(title)
                                                .setBody(body)
                                                .build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                                          .setAps(Aps.builder()
                                                     .setAlert(ApsAlert.builder()
                                                                       .setTitle(title)
                                                                       .setBody(body)
                                                                       .build()
                                                     )
                                                     .setSound(FcmContants.NOTIFICATION_DEFAULT_SOUND)
                                                     .build()
                                          )
                                          .build();

        Message message = Message.builder()
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
                throw BaseException.type(GlobalErrorCode.FCM_FAIL);
        }
    }
}