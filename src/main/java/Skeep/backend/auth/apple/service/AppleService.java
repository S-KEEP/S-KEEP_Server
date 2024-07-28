package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.AppleLoginRequest;
import Skeep.backend.auth.apple.dto.ApplePublicKeys;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.global.util.JwtUtil;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    private final ApplePublicKeyGenerator applePublicKeyGenerator;
    private final AppleAuthClient appleAuthClient;
    private final JwtUtil jwtUtil;
    private final AppleTokenUtil appleTokenUtil;

    public JwtDto login(AppleLoginRequest request) {
        String appleSerialId = getAppleSerialId(request.id_token());

        Long userId;
        if (userFindService.existUserByAppleSerialId(appleSerialId)) {
            userId = userFindService.findUserByAppleSerialId(appleSerialId).getId();
        } else {
            userId = signUp(appleSerialId, request.user());
        }

        return jwtUtil.generateTokens(userId);
    }

    @Transactional
    public Long signUp(String appleSerialId, AppleLoginRequest.AppleUser user) {
        return userService.saveAppleUser(appleSerialId, user.name().firstName() + user.name().lastName());
    }

    public String getAppleSerialId(String identityToken) {
        Map<String, String> headers = appleTokenUtil.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleAuthClient.getAppleAuthPublicKey();
        PublicKey publicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys);

        return appleTokenUtil.getTokenClaims(identityToken, publicKey).getSubject();
    }
}