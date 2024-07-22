package Skeep.backend.global.security.provider;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.global.security.info.JwtUserInfo;
import Skeep.backend.global.security.info.UserPrincipal;
import Skeep.backend.global.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailService customUserDetailService;

    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {
        log.info("AuthenticationProvider 진입 성공");
        if (!authentication.getPrincipal().getClass().equals(String.class)) {
            log.info("로그인 한 사용자 검증 과정");
            return authOfAfterLogin((JwtUserInfo) authentication.getPrincipal());
        } else {
            log.info("폼 또는 소셜 로그인 요청 감지 (비정상 요청)");
            throw new BaseException(GlobalErrorCode.VALIDATION_ERROR);
        }
    }

    private Authentication authOfAfterLogin(JwtUserInfo userInfo){
        UserPrincipal userPrincipal = customUserDetailService.loadUserById(userInfo.userId());
        return new UsernamePasswordAuthenticationToken(userPrincipal, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}