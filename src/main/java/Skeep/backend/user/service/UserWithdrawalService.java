package Skeep.backend.user.service;

import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.category.service.UserCategoryRemover;
import Skeep.backend.friend.service.FriendRemover;
import Skeep.backend.location.userLocation.service.UserLocationRemover;
import Skeep.backend.notification.service.NotificationRemover;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserWithdrawalService {
    private final UserFindService userFindService;
    private final JwtTokenService jwtTokenService;

    private final UserRemover userRemover;
    private final NotificationRemover notificationRemover;
    private final UserLocationRemover userLocationRemover;
    private final UserCategoryRemover userCategoryRemover;
    private final FriendRemover friendRemover;

    @Transactional
    public void withdrawal(Long userId) {
        jwtTokenService.deleteRefreshToken(userId);

        User user = userFindService.findById(userId);

        notificationRemover.deleteByUser(user);

        userLocationRemover.deleteByUser(user);
        userCategoryRemover.deleteByUser(user);
        friendRemover.deleteByUser(user);

        userRemover.deleteUser(user);
    }
}
