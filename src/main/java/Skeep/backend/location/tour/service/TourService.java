package Skeep.backend.location.tour.service;

import Skeep.backend.global.exception.BaseException;
import Skeep.backend.location.tour.dto.response.TourLocationList;
import Skeep.backend.location.tour.dto.response.TourLocationResponse;
import Skeep.backend.location.tour.exception.TourErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TourService {
    private final KoreanTourInfoService koreanTourInfoService;

    @Value("${tour.service-key.encoding}")
    private String serviceKey;

    private static int numOfRows = 3;
    private static int pageNo = 1;
    private static String mobileOS = "ETC";
    private static String mobileApp = "SKEEP";
    private static String arrange = "S";
    private static int radius = 20000;
    private static String listYN = "Y";

    public TourLocationList getLocationBasedList(double mapX, double mapY) {
        String response = koreanTourInfoService.getLocationBasedList(
                serviceKey, numOfRows, pageNo, mobileOS, mobileApp, arrange, mapX, mapY, radius, listYN, "json");
        TourLocationResponse tourLocationResponse = deserializeTourLocationResponse(response);

        if (tourLocationResponse.response().body().items() == null ||
                tourLocationResponse.response().body().items().item() == null) {
            return new TourLocationList(0, null);
        }

        List<TourLocationList.TourLocationDto> tourLocationDtos = convertToLocationDtos(tourLocationResponse);
        return new TourLocationList(tourLocationDtos.size(), tourLocationDtos);
    }

    private TourLocationResponse deserializeTourLocationResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        TourLocationResponse tourLocationResponse = null;
        try {
            tourLocationResponse = objectMapper.readValue(response, TourLocationResponse.class);
        } catch (JsonProcessingException e) {
            throw BaseException.type(TourErrorCode.CANNOT_CONVERT_RESPONSE);
        }

        return tourLocationResponse;
    }

    private List<TourLocationList.TourLocationDto> convertToLocationDtos(TourLocationResponse response) {
        return response.response()
                .body()
                .items()
                .item()
                .stream()
                .map(tourLocation -> new TourLocationList.TourLocationDto(
                        tourLocation.title(),
                        tourLocation.mapx(),
                        tourLocation.mapy(),
                        String.join(" ", tourLocation.addr1()),
                        tourLocation.dist(),
                        tourLocation.firstimage()
                ))
                .collect(Collectors.toList());
    }
}