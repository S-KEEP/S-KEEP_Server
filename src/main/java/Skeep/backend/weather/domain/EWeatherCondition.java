package Skeep.backend.weather.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EWeatherCondition {
    CLEAR("맑음"),
    PARTLY_CLOUDY("구름많음"),
    CLOUDY("흐림"),
    RAIN("비"),
    RAIN_AND_SNOW("비/눈"),
    SHOWER("소나기"),
    SNOW("눈");

    private final String description;

    public static EWeatherCondition convertMiddle(String weatherCondition) {
        switch (weatherCondition) {
            case "맑음":
                return EWeatherCondition.CLEAR;

            case "구름많음":
                return EWeatherCondition.PARTLY_CLOUDY;

            case "구름많고 비":
            case "흐리고 비":
                return EWeatherCondition.RAIN;

            case "구름많고 눈":
            case "흐리고 눈":
                return EWeatherCondition.SNOW;

            case "구름많고 비/눈":
            case "흐리고 비/눈":
                return EWeatherCondition.RAIN_AND_SNOW;

            case "구름많고 소나기":
            case "흐리고 소나기":
            case "소나기":
                return EWeatherCondition.SHOWER;

            case "흐림":
                return EWeatherCondition.CLOUDY;

            default:
                return null;
        }
    }
}