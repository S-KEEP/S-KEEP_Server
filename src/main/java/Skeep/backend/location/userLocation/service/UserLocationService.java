package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.dto.request.UserLocationGetDto;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchDto;
import Skeep.backend.location.userLocation.dto.response.LocationDto;
import Skeep.backend.location.userLocation.dto.response.UserCategoryDto;
import Skeep.backend.location.userLocation.dto.response.UserLocationDto;
import Skeep.backend.location.userLocation.dto.response.UserLocationListDto;
import Skeep.backend.s3.service.S3Service;
import Skeep.backend.screenshot.dto.request.ScreenshotUploadDto;
import Skeep.backend.screenshot.service.ScreenshotService;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLocationService {
    private final ScreenshotService screenshotService;
    private final UserFindService userFindService;
    private final UserLocationRetriever userLocationRetriever;
    private final UserLocationUpdater userLocationUpdater;
    private final S3Service s3Service;
    private final UserCategoryRepository userCategoryRepository;

    public UserLocationListDto getUserLocationListByUserCategory(
            Long userId,
            UserLocationGetDto userLocationGetDto
    ) {
        // Request 검증 로직
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        List<UserLocation> userLocationList
                = userLocationRetriever.findAllByUserIdAndFixedCategory(
                                                currentUser.getId(),
                                                userLocationGetDto.categoryName()
                                        );

        // TODO: query 계속 날리는 부분 추후에 성능 개선
        List<UserLocationDto> userLocationDtoList = userLocationList.stream()
                .map(userLocation -> {
                    Location tempLocation = userLocation.getLocation();
                    UserCategory tempUserCategory = userLocation.getUserCategory();
                    return UserLocationDto.of(
                            s3Service.getPresignUrl(userLocation.getFileName()),
                            LocationDto.of(
                                    tempLocation.getId(),
                                    tempLocation.getKakaoMapId(),
                                    tempLocation.getX(),
                                    tempLocation.getY(),
                                    tempLocation.getFixedCategory()
                            ),
                            tempUserCategory != null ?
                                UserCategoryDto.of(
                                    tempUserCategory.getId(),
                                    tempUserCategory.getName(),
                                    tempUserCategory.getDescription()
                                ) : null
                        );
                    }
                ).toList();

        return UserLocationListDto.of(
            userLocationDtoList
        );
    }

    public URI createUserLocation(
            Long userId,
            ScreenshotUploadDto screenshotUploadDto
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        List<UserLocation> userLocationList
                = screenshotService.analyzeImageAndSaveResult(currentUser, screenshotUploadDto);
        return URI.create("");
    }

    @Transactional
    public Boolean updateUserLocationWithUserCategory(
            Long userId,
            Long userLocationId,
            UserLocationPatchDto userLocationPatchDto
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        UserLocation targetUserLocation = userLocationRetriever.findByUserAndId(currentUser, userLocationId);

        // ToDo: UserCategoryRetriever 구현되면 그때 코드 수정할 것
        UserCategory targetUserCategory = userCategoryRepository.findById(userLocationPatchDto.userCategoryId())
                .orElseThrow(() -> BaseException.type(GlobalErrorCode.NOT_FOUND));

        userLocationUpdater.updateUserCategory(targetUserLocation, targetUserCategory);

        return Boolean.TRUE;
    }
}
