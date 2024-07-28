package Skeep.backend.auth.apple.service;

import Skeep.backend.auth.apple.dto.ApplePublicKey;
import Skeep.backend.auth.apple.dto.ApplePublicKeys;
import Skeep.backend.auth.exception.OAuthErrorCode;
import Skeep.backend.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplePublicKeyGenerator {
    public PublicKey generatePublicKey(Map<String, String> tokenHeaders, ApplePublicKeys applePublicKeys) {
        ApplePublicKey publicKey = applePublicKeys.getMatchedKey(tokenHeaders.get("kid"), tokenHeaders.get("alg"));

        return getPublicKey(publicKey);
    }

    private PublicKey getPublicKey(ApplePublicKey publicKey){
        byte[] nBytes = Base64.getUrlDecoder().decode(publicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(publicKey.e());

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(publicKey.kty());
        } catch (NoSuchAlgorithmException e) {
            throw new BaseException(OAuthErrorCode.NOT_FOUND_ALGORITHM);
        }

        try {
            return keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new BaseException(OAuthErrorCode.NOT_SUPPORTED_ALGORITHM);
        }
    }
}