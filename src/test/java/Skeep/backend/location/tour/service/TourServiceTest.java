package Skeep.backend.location.tour.service;

import Skeep.backend.global.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TourServiceTest extends ServiceTest {
    @Autowired
    private TourService tourService;

    @Test
    void getLocationBasedList() {
        System.out.println(tourService.getLocationBasedList(
                126.981611,
                37.568477
        ));
    }
}
