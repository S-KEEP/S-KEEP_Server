package Skeep.backend.user.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import Skeep.backend.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserFindService {
    private final UserRepository userRepository;

    public Boolean existUserByAppleSerialId(String appleSerialId) {
        return userRepository.findByAppleSerialId(appleSerialId).isPresent();
    }

    public User findUserByAppleSerialId(String appleSerialId) {
        return userRepository.findByAppleSerialId(appleSerialId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.NOT_FOUND_USER));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.NOT_FOUND_USER));
    }
}
