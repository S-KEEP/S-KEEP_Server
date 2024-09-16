package Skeep.backend.fcm.config;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FcmConfig {
    @PostConstruct
    public void init() {
        try {
            InputStream aboutFirebaseFile = new ClassPathResource("skeep-fcm.json").getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                                                     .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
                                                     .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialized {}", FirebaseApp.getInstance().getName());
            }
        } catch (IOException e) {
            log.error("FirebaseApp initialize failed : {}", e.getMessage());
            throw BaseException.type(GlobalErrorCode.FIREBASE_JSON_EMPTY);
        }
    }
}
