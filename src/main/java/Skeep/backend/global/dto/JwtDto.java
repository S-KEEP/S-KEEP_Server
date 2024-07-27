package Skeep.backend.global.dto;

public record JwtDto(
        String accessToken,
        String refreshToken
) {
}
