package Skeep.backend.user.service;

import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import Skeep.backend.user.domain.EStatus;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserWithdrawalService {
    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;
    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    @Async
    @Scheduled(cron = "0 50 23 * * *")
    public void withdrawal() {
        List<User> withdrawalUsers = userRepository.findUsersNotModifiedSince(LocalDateTime.now().minus(7, ChronoUnit.DAYS), EStatus.DEACTIVATED);
        withdrawalUsers.forEach(user -> {
            userLocationRepository.deleteAllByUser(user);
            userCategoryRepository.deleteAllByUser(user);
        });
        userRepository.deleteUsersNotModifiedSince(LocalDateTime.now().minus(7, ChronoUnit.DAYS), EStatus.DEACTIVATED);
    }
}
