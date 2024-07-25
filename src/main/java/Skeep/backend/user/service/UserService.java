package Skeep.backend.user.service;

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

    public Long saveAppleUser(String appleSerialId, String name) {
        return userRepository.save(User.builder()
                        .appleSerialId(appleSerialId)
                        .name(name)
                        .build()).getId();
    }
}