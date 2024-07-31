package Skeep.backend.kakaoMap.dto.response;

import lombok.Builder;

@Builder
public record KakaoResponseResult(
    String id,
    String x,
    String y
) {
    public static KakaoResponseResult of(
            final String id,
            final String x,
            final String y
    ) {
        return KakaoResponseResult.builder()
                .id(id)
                .x(x)
                .y(y)
                .build();
    }
}
