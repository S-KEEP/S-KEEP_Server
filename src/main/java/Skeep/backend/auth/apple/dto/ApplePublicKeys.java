package Skeep.backend.auth.apple.dto;

import javax.naming.AuthenticationException;
import java.util.List;

public record ApplePublicKeys (
        List<ApplePublicKey> keys
){
    public ApplePublicKey getMatchedKey(String kid, String alg) throws AuthenticationException {
        return keys.stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findAny()
                .orElseThrow(AuthenticationException::new);
    }
}