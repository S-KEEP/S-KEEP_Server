package Skeep.backend.tour.service;

import Skeep.backend.tour.dto.response.TourLocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void getLocationBasedList(double mapX, double mapY) {
        TourLocationResponse tourLocationResponse = koreanTourInfoService.getLocationBasedList(
                serviceKey,
                numOfRows,
                pageNo,
                mobileOS,
                mobileApp,
                arrange,
                mapX,
                mapY,
                radius,
                listYN,
                "json"
        );
        System.out.println(tourLocationResponse);
    }
}