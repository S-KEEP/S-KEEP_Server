package Skeep.backend.user.service;

import Skeep.backend.auth.apple.service.AppleService;
import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.user.domain.EStatus;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final JwtTokenService jwtTokenService;
    private final AppleService appleService;

    @Transactional
    public Long saveAppleUser(String appleSerialId, String name) {
        return userRepository.save(User.createAppleUser(appleSerialId, name)).getId();
    }

    @Transactional
    public void withdrawalUser(Long userId) {
        User user = userFindService.findById(userId);
        user.updateStatus(EStatus.DEACTIVATED);

        jwtTokenService.deleteRefreshToken(userId);
    }
}