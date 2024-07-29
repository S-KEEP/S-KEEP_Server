package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.AppleAuthTokenResponse;
import Skeep.backend.auth.apple.dto.AppleLoginRequest;
import Skeep.backend.auth.apple.dto.ApplePublicKeys;
import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.global.util.JwtUtil;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        return userService.saveAppleUser(appleSerialId, user.name().firstName() + user.name().lastName());
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

        appleRevokeClient.revokeToken(clientSecret, refreshToken, appleOAuthManager.aud);
    }

    public String getAppleRefreshToken(String code, String clientSecret) {
        AppleAuthTokenResponse response = appleOAuthTokenClient.generateAuthToken(code, appleOAuthManager.aud, clientSecret, "authorization_code");
        return response.refresh_token();
    }
}