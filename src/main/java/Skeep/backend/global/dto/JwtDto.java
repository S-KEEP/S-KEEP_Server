package Skeep.backend.global.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record JwtDto(
        String accessToken,
        String refreshToken
) implements Serializable {
    public static JwtDto of(String accessToken, String refreshToken) {
        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
