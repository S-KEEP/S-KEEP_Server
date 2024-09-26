package Skeep.backend.location.picks.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import Skeep.backend.kakaoMap.service.KakaoMapService;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.picks.domain.Picks;
import Skeep.backend.location.picks.domain.PicksRepository;
import Skeep.backend.location.picks.dto.request.PicksRequest;
import Skeep.backend.location.tour.dto.TourLocationDto;
import Skeep.backend.location.tour.dto.response.TourLocationList;
import Skeep.backend.location.tour.exception.TourErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PicksService {
    private final PicksRepository picksRepository;
    private final KakaoMapService kakaoMapService;

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

    public TourLocationList findAll() {
        return new TourLocationList(
                picksRepository.findAll().size(),
                picksRepository.findAll().stream()
                        .map(pick -> new TourLocationDto(
                                pick.getPlaceName(),
                                pick.getX(),
                                pick.getY(),
                                pick.getRoadAddress(),
                                null,
                                null,
                                pick.getFileName()
                        ))
                        .toList()
        );
    }
}
