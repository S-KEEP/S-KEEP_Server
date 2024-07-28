package Skeep.backend.auth.apple.dto;

import Skeep.backend.auth.exception.AuthErrorCode;
import Skeep.backend.global.exception.BaseException;

import java.util.List;

public record ApplePublicKeys (
        List<ApplePublicKey> keys
){
    public ApplePublicKey getMatchedKey(String kid, String alg) {
        return keys.stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findAny()
                .orElseThrow(() -> new BaseException(AuthErrorCode.NOT_MATCHED_PUBLIC_KEY));
    }
}