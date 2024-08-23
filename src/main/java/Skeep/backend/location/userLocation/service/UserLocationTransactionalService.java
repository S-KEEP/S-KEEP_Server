package Skeep.backend.location.userLocation.service;

import Skeep.backend.category.domain.ECategory;
import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.category.service.UserCategorySaver;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.kakaoMap.dto.response.KakaoResponseResult;
import Skeep.backend.location.location.domain.Location;
import Skeep.backend.location.location.service.LocationRetriever;
import Skeep.backend.location.location.service.LocationSaver;
import Skeep.backend.location.userLocation.domain.UserLocation;
import Skeep.backend.location.userLocation.exception.UserLocationErrorCode;
import Skeep.backend.s3.service.S3Service;
import Skeep.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserLocationTransactionalService {

    private final UserLocationSaver userLocationSaver;
    private final UserLocationUpdater userLocationUpdater;
    private final LocationRetriever locationRetriever;
    private final LocationSaver locationSaver;
    private final UserCategorySaver userCategorySaver;
    private final S3Service s3Service;
    private final UserCategoryRepository userCategoryRepository;

    @Transactional
    public UserLocation updateUserLocation(
            User currentUser,
            UserLocation userLocation,
            KakaoResponseResult kakaoResponseResult,
            ECategory category
    ) {
        Location location;
        if (locationRetriever.existsByKakaoMapId(kakaoResponseResult.id()))
            location = locationRetriever.findByKakaoMapId(kakaoResponseResult.id());
        else
            location = getLocation(kakaoResponseResult, category);
        UserCategory userCategory = getCategory(currentUser, location);

        return userLocationUpdater.updateUserLocation(userLocation, location, userCategory);
    }

    @Transactional
    public UserLocation readyAndUploadToS3(
            User currentUser,
            MultipartFile file,
            KakaoResponseResult kakaoResponseResult,
            ECategory category
    ) {
        UserLocation userLocation = userLocationSaver.createUserLocation(currentUser);
        String fileName = s3Service.uploadToS3(userLocation.getId(), file);
        Location location;
        if (locationRetriever.existsByKakaoMapId(kakaoResponseResult.id()))
            location = locationRetriever.findByKakaoMapId(kakaoResponseResult.id());
        else
            location = getLocation(kakaoResponseResult, category);
        UserCategory userCategory = getCategory(currentUser, location);

        return userLocationUpdater.updateUserLocation(userLocation, fileName, location, userCategory);
    }

    private UserCategory getCategory(
            final User currentUser,
            final Location location
    ) {
        // TODO: UserCategoryRetriever 생기면 그때 이 로직 수정할 것
        return userCategoryRepository
                .findByUserAndName(currentUser, location.getFixedCategory().getName())
                .orElseThrow(() -> BaseException.type(UserLocationErrorCode.INVALID_CATEGORY));
    }

    private Location getLocation(
            final KakaoResponseResult kakaoResponseResult,
            final ECategory category
    ) {
        return locationSaver.saveLocation(
                Location.builder()
                        .kakaoMapId(kakaoResponseResult.id())
                        .placeName(kakaoResponseResult.placeName())
                        .roadAddress(kakaoResponseResult.roadAddress())
                        .x(kakaoResponseResult.x())
                        .y(kakaoResponseResult.y())
                        .fixedCategory(category)
                        .build()
        );
    }

}
