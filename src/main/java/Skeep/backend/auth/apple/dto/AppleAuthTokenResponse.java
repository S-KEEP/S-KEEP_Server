package Skeep.backend.auth.apple.dto;

public record AppleAuthTokenResponse(
        String accessToken,
        Integer expires_in,
        String id_token,
        String refresh_token,
        String token_type
) {
}