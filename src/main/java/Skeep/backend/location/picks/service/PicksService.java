package Skeep.backend.location.picks.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import Skeep.backend.kakaoMap.service.KakaoMapService;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.service.LocationRetriever;
import Skeep.backend.location.picks.domain.Picks;
import Skeep.backend.location.picks.domain.PicksRepository;
import Skeep.backend.location.picks.dto.request.PicksRequest;
import Skeep.backend.location.picks.dto.response.PicksDto;
import Skeep.backend.location.picks.dto.response.PicksDtoList;
import Skeep.backend.location.tour.exception.TourErrorCode;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.service.UserLocationSaver;
import Skeep.backend.s3.service.S3Service;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PicksService {
    private final PicksRepository picksRepository;
    private final KakaoMapService kakaoMapService;
    private final S3Service s3Service;
    private final LocationRetriever locationRetriever;
    private final UserFindService userFindService;
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserLocationSaver userLocationSaver;

    @Transactional
    public void save(PicksRequest request) {
        List<KakaoResponseResult> kakaoLocationIdList = kakaoMapService.getKakaoLocationIdList(List.of(request.placeName()));
        if (kakaoLocationIdList.isEmpty()) {
            throw BaseException.type(TourErrorCode.CANNOT_MATCH_KAKAO_MAP);
        }

        picksRepository.save(Picks.createPicks(
                kakaoLocationIdList.get(0).id(),
                kakaoLocationIdList.get(0).placeName(),
                kakaoLocationIdList.get(0).roadAddress(),
                kakaoLocationIdList.get(0).x(),
                kakaoLocationIdList.get(0).y(),
                request.fileName(),
                ECategory.findByName(request.categoryName()),
                request.editorName()
                ));
    }

    public PicksDtoList findAll() {
        return new PicksDtoList(
                picksRepository.findAll().size(),
                picksRepository.findAll().stream()
                        .map(pick -> new PicksDto(
                                pick.getPlaceName(),
                                pick.getRoadAddress(),
                                pick.getX(),
                                pick.getY(),
                                s3Service.getPresignUrl(pick.getFileName()),
                                pick.getFixedCategory().getName(),
                                pick.getEditorName()
                        ))
                        .toList()
        );
    }

    @Transactional
    public void deleteAll() {
        picksRepository.deleteAll();;
    }

    @Transactional
    public Long addUserCategory(Long userId, Long userCategoryId, String title) {
        Picks picks = picksRepository.findByPlaceName(title).get();
        Location location = locationRetriever.findByKakaoMapId(picks.getKakaoMapId());
        User user = userFindService.findById(userId);
        UserCategory userCategory = userCategoryRetriever.findById(userCategoryId);

        return userLocationSaver.createUserLocation(UserLocation.createUserLocation(picks.getFileName(), location, user, userCategory)).getId();
    }
}
