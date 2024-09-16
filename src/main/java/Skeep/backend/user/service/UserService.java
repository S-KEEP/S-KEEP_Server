package Skeep.backend.user.service;

import Skeep.backend.user.domain.ERole;
import Skeep.backend.user.domain.Email;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import Skeep.backend.user.dto.request.UserFcmTokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserFindService userFindService;
    private final UserUpdater userUpdater;
    private final UserRepository userRepository;

    @Transactional
    public User saveAppleUser(String appleSerialId, String name, String email) {
        return userRepository.save(
                User.createAppleUser(appleSerialId, name, Email.createEmail(email), ERole.USER)
        );
    }

    @Transactional
    public Void initFcmToken(
            Long userId,
            UserFcmTokenRequestDto userFcmTokenRequestDto
    ) {
        User currentUser = userFindService.findById(userId);
        String fcmToken = userFcmTokenRequestDto.token();

        userUpdater.updateUserFcmToken(currentUser, fcmToken);

        return null;
    }
}