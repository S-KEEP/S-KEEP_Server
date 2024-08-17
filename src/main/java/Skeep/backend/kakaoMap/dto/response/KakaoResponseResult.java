package Skeep.backend.kakaoMap.dto.response;

import lombok.Builder;

@Builder
public record KakaoResponseResult(
    String id,
    String roadAddress,
    String x,
    String y
) {
    public static KakaoResponseResult of(
            final String id,
            final String roadAddress,
            final String x,
            final String y
    ) {
        return KakaoResponseResult.builder()
                .id(id)
                .roadAddress(roadAddress)
                .x(x)
                .y(y)
                .build();
    }
}
