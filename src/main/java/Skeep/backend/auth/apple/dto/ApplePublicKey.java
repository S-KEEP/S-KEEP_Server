package Skeep.backend.auth.apple.dto;

public record ApplePublicKey(
        String kty,
        String kid,
        String alg,
        String n,
        String e
) {
}