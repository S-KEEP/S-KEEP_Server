package Skeep.backend.global.security.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.security.info.UserPrincipal;
import Skeep.backend.user.domain.UserRepository;
import Skeep.backend.user.dto.UserSecurityForm;
import Skeep.backend.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSecurityForm userSecurityForm = userRepository.findUserSecurityFromByAppleSerialId(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));
        log.info(("아이디 기반 조회 성공"));
        return UserPrincipal.create(userSecurityForm);
    }

    public UserPrincipal loadUserById(Long id) {
        UserSecurityForm userSecurityForm = userRepository.findUserSecurityFromById(id).orElseThrow(() -> new BaseException(UserErrorCode.NOT_FOUND_USER));
        log.info("user id 기반 조회 성공");

        return UserPrincipal.create(userSecurityForm);
    }
}
