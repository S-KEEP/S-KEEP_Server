package Skeep.backend.weather.dto.response;

import java.util.List;

public record MiddleTermTaResponse(
        Response response
) {
    public record Response(
            Header header,
            Body body
    ) {
        public record Header(
                String resultCode,
                String resultMsg
        ) {
        }

        public record Body(
                String dataType,
                Items items,
                int numOfRows,
                int pageNo,
                int totalCount
        ) {
            public record Items(
                    List<Item> item
            ) {
                public record Item(
                        String regId,     // 예보구역코드
                        int taMin3,       // 3일 후 최저기온
                        int taMin3Low,    // 3일 후 최저기온 예상 범위 하한
                        int taMin3High,   // 3일 후 최저기온 예상 범위 상한
                        int taMax3,       // 3일 후 최고기온
                        int taMax3Low,    // 3일 후 최고기온 예상 범위 하한
                        int taMax3High,   // 3일 후 최고기온 예상 범위 상한
                        int taMin4,       // 4일 후 최저기온
                        int taMin4Low,    // 4일 후 최저기온 예상 범위 하한
                        int taMin4High,   // 4일 후 최저기온 예상 범위 상한
                        int taMax4,       // 4일 후 최고기온
                        int taMax4Low,    // 4일 후 최고기온 예상 범위 하한
                        int taMax4High,   // 4일 후 최고기온 예상 범위 상한
                        int taMin5,       // 5일 후 최저기온
                        int taMin5Low,    // 5일 후 최저기온 예상 범위 하한
                        int taMin5High,   // 5일 후 최저기온 예상 범위 상한
                        int taMax5,       // 5일 후 최고기온
                        int taMax5Low,    // 5일 후 최고기온 예상 범위 하한
                        int taMax5High,   // 5일 후 최고기온 예상 범위 상한
                        int taMin6,       // 6일 후 최저기온
                        int taMin6Low,    // 6일 후 최저기온 예상 범위 하한
                        int taMin6High,   // 6일 후 최저기온 예상 범위 상한
                        int taMax6,       // 6일 후 최고기온
                        int taMax6Low,    // 6일 후 최고기온 예상 범위 하한
                        int taMax6High,   // 6일 후 최고기온 예상 범위 상한
                        int taMin7,       // 7일 후 최저기온
                        int taMin7Low,    // 7일 후 최저기온 예상 범위 하한
                        int taMin7High,   // 7일 후 최저기온 예상 범위 상한
                        int taMax7,       // 7일 후 최고기온
                        int taMax7Low,    // 7일 후 최고기온 예상 범위 하한
                        int taMax7High,   // 7일 후 최고기온 예상 범위 상한
                        int taMin8,       // 8일 후 최저기온
                        int taMin8Low,    // 8일 후 최저기온 예상 범위 하한
                        int taMin8High,   // 8일 후 최저기온 예상 범위 상한
                        int taMax8,       // 8일 후 최고기온
                        int taMax8Low,    // 8일 후 최고기온 예상 범위 하한
                        int taMax8High,   // 8일 후 최고기온 예상 범위 상한
                        int taMin9,       // 9일 후 최저기온
                        int taMin9Low,    // 9일 후 최저기온 예상 범위 하한
                        int taMin9High,   // 9일 후 최저기온 예상 범위 상한
                        int taMax9,       // 9일 후 최고기온
                        int taMax9Low,    // 9일 후 최고기온 예상 범위 하한
                        int taMax9High,   // 9일 후 최고기온 예상 범위 상한
                        int taMin10,      // 10일 후 최저기온
                        int taMin10Low,   // 10일 후 최저기온 예상 범위 하한
                        int taMin10High,  // 10일 후 최저기온 예상 범위 상한
                        int taMax10,      // 10일 후 최고기온
                        int taMax10Low,   // 10일 후 최고기온 예상 범위 하한
                        int taMax10High   // 10일 후 최고기온 예상 범위 상한
                ) {
                }
            }
        }
    }
}