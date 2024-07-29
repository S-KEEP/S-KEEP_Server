package Skeep.backend.global.security.handler.logout;

import Skeep.backend.auth.jwt.domain.RefreshTokenRepository;
import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.global.constant.Constants;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.global.util.HeaderUtil;
import Skeep.backend.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomLogoutProcessHandler implements LogoutHandler {
    private final JwtTokenService jwtTokenService;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication == null) {
            throw new BaseException(GlobalErrorCode.UNAUTHORIZED);
        }

        String accessToken = HeaderUtil.refineHeader(request, Constants.PREFIX_AUTH, Constants.PREFIX_BEARER)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.INVALID_HEADER_VALUE));

        Claims claims = jwtUtil.validateToken(accessToken);
        jwtTokenService.deleteRefreshToken(claims.get(Constants.CLAIM_USER_ID, Long.class));
    }
}
