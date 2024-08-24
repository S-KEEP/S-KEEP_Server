package Skeep.backend.tour.service;

import Skeep.backend.tour.dto.response.TourLocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="koreanTourInfoService", url="http://apis.data.go.kr/B551011/KorService1")
public interface KoreanTourInfoService {
    @GetMapping("/locationBasedList1")
    TourLocationResponse getLocationBasedList(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("MobileOS") String mobileOS,
            @RequestParam("MobileApp") String mobileApp,
            @RequestParam("arrange") String arrange,
            @RequestParam("mapX") double mapX,
            @RequestParam("mapY") double mapY,
            @RequestParam("radius") int radius,
            @RequestParam("listYN") String listYN,
            @RequestParam("_type") String type
    );
}
