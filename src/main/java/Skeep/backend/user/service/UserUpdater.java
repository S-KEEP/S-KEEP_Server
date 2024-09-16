package Skeep.backend.user.service;

import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdater {
    public void updateUserFcmToken(User user, String token) {
        user.updateFcmToken(token);
    }
}
