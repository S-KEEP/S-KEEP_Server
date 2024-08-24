package Skeep.backend.tour.service;

import Skeep.backend.global.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[Service Layer] -> TourService")
class TourServiceTest extends ServiceTest {
    @Autowired
    private TourService tourService;

    @Test
    void TourAPI에서_위치정보기반의_장소_리스트를_받아온다() {
        tourService.getLocationBasedList(126.983745, 37.563446);
    }
}