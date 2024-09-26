package Skeep.backend.weather.service;

import Skeep.backend.weather.dto.response.MiddleTermLandForecastResponse;
import Skeep.backend.weather.dto.response.MiddleTermTaResponse;
import Skeep.backend.weather.dto.response.ShortTermResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeatherService {
    private final ShortTermFeignClientService shortTermFeignClientService;
    private final MediumTermFeignClientService mediumTermFeignClientService;

    @Value("${weather.service-key.decoding}")
    private String serviceKey;

    public ShortTermResponse.Response.Body.Items getShortTermForecast(LocalDate localDate, String x, String y) {
        String response = shortTermFeignClientService.getVillageForecast(
                serviceKey, 1000, 1, "JSON",
                localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")), "0500", x, y);

        ShortTermResponse shorTermResponse = deserializeShortTermResponse(response);
        System.out.println("*************");
        System.out.println(shorTermResponse);
        if (shorTermResponse == null || Objects.equals(shorTermResponse.response().header().resultCode(), "03")) {
            return null;
        }

        return shorTermResponse.response().body().items();
    }

    private ShortTermResponse deserializeShortTermResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        ShortTermResponse shorTermResponse = null;
        try {
            shorTermResponse = objectMapper.readValue(response, ShortTermResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return shorTermResponse;
    }

    public MiddleTermLandForecastResponse.Response.Body.Items getMiddleTermLandForecast(LocalDate localDate, String regionCode) {
        String response = mediumTermFeignClientService.getMidLandForecast(
                serviceKey, 10, 1, "JSON",
                regionCode,
                localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0600"
        );

        System.out.println("middle term - land response");
        System.out.println(response);

        MiddleTermLandForecastResponse middleTermLandForecastResponse = deserializeMiddleTermLandForecastResponse(response);
        if (Objects.equals(middleTermLandForecastResponse.response().header().resultCode(), "03")) {
            return null;
        }
        return middleTermLandForecastResponse.response().body().items();
    }

    private MiddleTermLandForecastResponse deserializeMiddleTermLandForecastResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        MiddleTermLandForecastResponse middleTermLandForecastResponse = null;
        try {
            middleTermLandForecastResponse = objectMapper.readValue(response, MiddleTermLandForecastResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return middleTermLandForecastResponse;
    }

    public MiddleTermTaResponse.Response.Body.Items getMiddleTermTa(LocalDate localDate, String regionCode) {
        String response = mediumTermFeignClientService.getMidTaForecast(
                serviceKey, 10, 1, "JSON",
                regionCode,
                localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "0600"
        );

        System.out.println("middle term - ta response");
        System.out.println(response);

        MiddleTermTaResponse middleTermTaResponse = deserializeMiddleTermTaResponse(response);
        return middleTermTaResponse.response().body().items();
    }

    private MiddleTermTaResponse deserializeMiddleTermTaResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        MiddleTermTaResponse middleTermTaResponse = null;
        try {
            middleTermTaResponse = objectMapper.readValue(response, MiddleTermTaResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return middleTermTaResponse;
    }
}
