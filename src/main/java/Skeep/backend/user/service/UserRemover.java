package Skeep.backend.user.service;

import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserRemover {
    private final UserRepository userRepository;

    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
