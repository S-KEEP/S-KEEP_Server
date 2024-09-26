package Skeep.backend.user.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.user.domain.EStatus;
import Skeep.backend.user.domain.Email;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.domain.UserRepository;
import Skeep.backend.user.dto.UserSecurityForm;
import Skeep.backend.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserFindService {
    private final UserRepository userRepository;

    public Boolean existUserBySerialId(String serialId) {
        return userRepository.findBySerialId(serialId).isPresent();
    }

    public UserSecurityForm findUserSecurityFromBySerialId(String serialId) {
        return userRepository.findUserSecurityFromBySerialId(serialId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.NOT_FOUND_USER));
    }

    public UserSecurityForm findUserSecurityFromById(Long userId) {
        return userRepository.findUserSecurityFromById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.NOT_FOUND_USER));
    }

    public User findUserBySerialId(String serialId) {
        return userRepository.findBySerialId(serialId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.NOT_FOUND_USER));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(Email.createEmail(email))
                .orElseThrow(() -> BaseException.type(UserErrorCode.NOT_FOUND_USER));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> BaseException.type(UserErrorCode.NOT_FOUND_USER));
    }

    public User findUserByIdAndStatus(final Long userId) {
        return userRepository.findByIdAndStatus(userId, EStatus.ACTIVATED)
                .orElseThrow(() -> BaseException.type(UserErrorCode.DEACTIVATED_USER));
    }

    public Page<User> findAllUserInFriend(User user, Pageable pageable) {
        return userRepository.findAllByUserInFriend(user.getId(), pageable);
    }
}
