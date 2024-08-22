package Skeep.backend.user.service;

import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserWithdrawalService {
    private final UserFindService userFindService;
    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;
    private final UserCategoryRepository userCategoryRepository;


    @Transactional
    public void withdrawal(Long userId) {
        jwtTokenService.deleteRefreshToken(userId);

        User user = userFindService.findById(userId);

        userLocationRepository.deleteAllByUser(user);
        userCategoryRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }
}
