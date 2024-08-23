package Skeep.backend.auth.jwt.service;

import Skeep.backend.auth.exception.AuthErrorCode;
import Skeep.backend.auth.jwt.domain.RefreshToken;
import Skeep.backend.auth.jwt.domain.RefreshTokenRepository;
import Skeep.backend.global.constant.Constants;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.util.JwtUtil;
import Skeep.backend.user.domain.ERole;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    public String reissueAccessToken(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            Long userId = getUserIdFromRefreshToken(refreshToken);
            ERole userRole = getUserRoleFromRefreshToken(refreshToken);
            return jwtUtil.generateAccessToken(userId, userRole);
        } else {
            throw BaseException.type(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    private Long getUserIdFromRefreshToken(String refreshToken) {
        Claims claims = jwtUtil.validateToken(refreshToken);
        return claims.get(Constants.CLAIM_USER_ID, Long.class);
    }

    private ERole getUserRoleFromRefreshToken(String refreshToken) {
        Claims claims = jwtUtil.validateToken(refreshToken);
        return ERole.valueOf(claims.get(Constants.CLAIM_USER_ROLE, String.class));
    }

    private boolean validateRefreshToken(String refreshToken) {
        Long userId = getUserIdFromRefreshToken(refreshToken);

        return refreshTokenRepository.findById(userId)
                .map(token -> Objects.equals(token.getToken(), refreshToken))
                .orElse(false);
    }

    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken) {
        refreshTokenRepository.findById(userId)
                .ifPresentOrElse(
                        existingToken -> {
                            refreshTokenRepository.deleteById(userId);
                            refreshTokenRepository.save(RefreshToken.issueRefreshToken(userId, refreshToken));
                        },
                        () -> refreshTokenRepository.save(RefreshToken.issueRefreshToken(userId, refreshToken))
                );
    }

    @Transactional
    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }
}
