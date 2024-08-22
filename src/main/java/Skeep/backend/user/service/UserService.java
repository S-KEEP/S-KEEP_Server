package Skeep.backend.user.service;

import Skeep.backend.user.domain.ERole;
import Skeep.backend.user.domain.Email;
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

    @Transactional
    public User saveAppleUser(String appleSerialId, String name, String email) {
        return userRepository.save(
                User.createAppleUser(appleSerialId, name, Email.createEmail(email), ERole.USER)
        );
    }
}