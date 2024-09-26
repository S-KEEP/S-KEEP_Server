package Skeep.backend.location.picks.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import Skeep.backend.kakaoMap.service.KakaoMapService;
import Skeep.backend.location.picks.domain.Picks;
import Skeep.backend.location.picks.domain.PicksRepository;
import Skeep.backend.location.picks.dto.request.PicksRequest;
import Skeep.backend.location.picks.dto.response.PicksDto;
import Skeep.backend.location.picks.dto.response.PicksDtoList;
import Skeep.backend.location.tour.exception.TourErrorCode;
import Skeep.backend.s3.service.S3Service;
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
    private final S3Service s3Service;

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
}
