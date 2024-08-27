package Skeep.backend.location.tour.service;

import Skeep.backend.global.ServiceTest;
import Skeep.backend.location.tour.dto.TourLocationDto;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static Skeep.backend.fixture.UserCategoryFixture.*;
import static Skeep.backend.fixture.UserFixture.ALICE_JOHNSON;

class TourServiceTest extends ServiceTest {
    @Autowired
    private TourService tourService;

    @Test
    void createUserLocationByTourAPI() {
        User user = userRepository.save(ALICE_JOHNSON.toUser(EProvider.APPLE));

        userCategoryRepository.save(ACTIVITY.toUserCategory(user));
        userCategoryRepository.save(PARK_NATURE.toUserCategory(user));
        userCategoryRepository.save(REST.toUserCategory(user));
        userCategoryRepository.save(HISTORY.toUserCategory(user));
        userCategoryRepository.save(CULTURE_FESTIVAL.toUserCategory(user));
        userCategoryRepository.save(SHOPPING_DOWNTOWN.toUserCategory(user));
        userCategoryRepository.save(EXTRA.toUserCategory(user));
        userCategoryRepository.save(RESTAURANT.toUserCategory(user));

        TourLocationDto tourLocationDto = new TourLocationDto(
                "개성만두 궁",
                "126.9854701651",
                "37.5743256886",
                "서울특별시 종로구 인사동10길 11-3",
                "734.2820517741083",
                "39",
                "http://tong.visitkorea.or.kr/cms/resource/44/1885344_image2_1.jpg"
        );

        System.out.println(tourService.createUserLocationByTourAPI(1L, tourLocationDto, 1L));
    }
}