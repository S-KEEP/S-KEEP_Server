package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.AppleAuthTokenResponse;
import Skeep.backend.auth.apple.dto.AppleLoginRequest;
import Skeep.backend.auth.apple.dto.ApplePublicKeys;
import Skeep.backend.auth.exception.AuthErrorCode;
import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.util.JwtUtil;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PublicKey;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppleService {
    private final UserService userService;
    private final UserFindService userFindService;
    private final JwtTokenService jwtTokenService;
    private final AppleOAuthManager appleOAuthManager;

    private final ApplePublicKeyClient applePublicKeyClient;
    private final AppleRevokeClient appleRevokeClient;
    private final AppleOAuthTokenClient appleOAuthTokenClient;

    private final JwtUtil jwtUtil;
    private final AppleTokenUtil appleTokenUtil;

    @Value("${apple.aud}")
    private String aud;

    @Transactional
    public JwtDto login(AppleLoginRequest request) {
        String appleSerialId = getAppleSerialId(request.id_token());

        Long userId;
        if (userFindService.existUserByAppleSerialId(appleSerialId)) {
            userId = userFindService.findUserByAppleSerialId(appleSerialId).getId();
        } else {
            userId = signUp(appleSerialId, request.user());
        }

        JwtDto jwtDto = jwtUtil.generateTokens(userId);
        jwtTokenService.updateRefreshToken(userId, jwtDto.refreshToken());
        return jwtDto;
    }

    @Transactional
    public Long signUp(String appleSerialId, AppleLoginRequest.AppleUser user) {
        return userService.saveAppleUser(appleSerialId, user.name().firstName() + user.name().lastName(), user.email());
    }

    public String getAppleSerialId(String identityToken) {
        Map<String, String> headers = appleTokenUtil.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = applePublicKeyClient.getAppleAuthPublicKey();
        PublicKey publicKey = appleOAuthManager.generatePublicKey(headers, applePublicKeys);

        return appleTokenUtil.getTokenClaims(identityToken, publicKey).getSubject();
    }

    public void revokeAppleUser(AppleLoginRequest request) {
        String clientSecret = appleOAuthManager.createClientSecret();
        String refreshToken = getAppleRefreshToken(request.code(), clientSecret);

        ResponseEntity<Void> response = appleRevokeClient.revokeToken(clientSecret, refreshToken, aud);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new BaseException(AuthErrorCode.APPLE_REVOKE_ERROR);
        }
    }

    public String getAppleRefreshToken(String code, String clientSecret) {
        AppleAuthTokenResponse response = appleOAuthTokenClient.generateAuthToken(code, aud, clientSecret, "authorization_code");
        return response.refresh_token();
    }
}