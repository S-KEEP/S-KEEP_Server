package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.dto.request.UserLocationPatchDto;
import Skeep.backend.location.userLocation.dto.response.*;
import Skeep.backend.location.userLocation.exception.UserLocationErrorCode;
import Skeep.backend.s3.service.S3Service;
import Skeep.backend.screenshot.dto.request.ScreenshotUploadDto;
import Skeep.backend.screenshot.service.ScreenshotService;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLocationService {
    private final ScreenshotService screenshotService;
    private final UserFindService userFindService;
    private final UserLocationRetriever userLocationRetriever;
    private final UserLocationUpdater userLocationUpdater;
    private final UserLocationRemover userLocationRemover;
    private final S3Service s3Service;
    private final UserCategoryRepository userCategoryRepository;

    public UserLocationListDto getUserLocationListByUserCategory(
            Long userId,
            String userCategory,
            int page
    ) {
        // Request 검증 로직
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        if (page < 1)
            throw BaseException.type(UserLocationErrorCode.INVALID_PAGE_USER_LOCATION);
        Pageable pageable = PageRequest.of(page - 1, 10);
        Page<UserLocation> userLocationPage
                = userLocationRetriever.findAllByUserIdAndUserCategory(
                                                currentUser.getId(),
                                                userCategory,
                                                pageable
                                        );

        List<UserLocation> userLocationList = userLocationPage.getContent();

        // TODO: query 계속 날리는 부분 추후에 성능 개선
        List<UserLocationDto> userLocationDtoList = userLocationList.stream()
                .map(userLocation -> {
                    Location tempLocation = userLocation.getLocation();
                    UserCategory tempUserCategory = userLocation.getUserCategory();
                    return UserLocationDto.of(
                            userLocation.getId(),
                            s3Service.getPresignUrl(userLocation.getFileName()),
                            LocationDto.of(tempLocation),
                            tempUserCategory != null ?
                                UserCategoryDto.of(tempUserCategory)
                                    : null
                        );
                    }
                ).toList();

        return UserLocationListDto.of(
                userLocationDtoList,
                userLocationPage.getTotalPages()
        );
    }

    public UserLocationCreate createUserLocation(
            Long userId,
            ScreenshotUploadDto screenshotUploadDto
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        List<UserLocation> userLocationList
                = screenshotService.analyzeImageAndSaveResult(currentUser, screenshotUploadDto);

        String uriString = userLocationList.stream()
                .map(userLocation -> "/user-location/" + userLocation.getId())
                .collect(Collectors.joining(","));

        List<UserLocationDto> userLocationDtoList =
                userLocationList.stream().map(
                        userLocation -> {
                            Location tempLocation = userLocation.getLocation();
                            UserCategory tempUserCategory = userLocation.getUserCategory();
                            return UserLocationDto.of(
                                    userLocation.getId(),
                                    s3Service.getPresignUrl(userLocation.getFileName()),
                                    LocationDto.of(tempLocation),
                                    tempUserCategory != null ?
                                            UserCategoryDto.of(tempUserCategory)
                                            : null
                            );
                        }
                ).toList();
        UserLocationCreateDto userLocationCreateDto = UserLocationCreateDto.of(userLocationDtoList);

        return UserLocationCreate.of(
                userLocationCreateDto,
                URI.create(uriString)
        );
    }

    public UserLocationDto getUserLocationRetrieve(Long userId, Long userLocationId) {

        User currentUser = userFindService.findUserByIdAndStatus(userId);

        UserLocation targetLocation
                = userLocationRetriever.findByUserAndId(currentUser, userLocationId);
        Location location = targetLocation.getLocation();
        UserCategory userCategory = targetLocation.getUserCategory();

        return UserLocationDto.of(
                userLocationId,
                s3Service.getPresignUrl(targetLocation.getFileName()),
                LocationDto.of(location),
                UserCategoryDto.of(userCategory)
        );
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

    @Transactional
    public void deleteUserLocation(Long userId, Long userLocationId) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);

        if (userLocationRetriever.existsByUserAndId(currentUser, userLocationId))
            userLocationRemover.deleteByUserIdAndId(currentUser, userLocationId);
        else
            throw BaseException.type(UserLocationErrorCode.MISMATCH_USER_AND_USER_LOCATION);
    }
}
