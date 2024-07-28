package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.AppleLoginRequest;
import Skeep.backend.auth.apple.dto.ApplePublicKeys;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.global.util.JwtUtil;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
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
        Map<String, String> headers = jwtUtil.parseHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleAuthClient.getAppleAuthPublicKey();
        PublicKey publicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeys);

        return jwtUtil.getTokenClaims(identityToken, publicKey).getSubject();
    }
}