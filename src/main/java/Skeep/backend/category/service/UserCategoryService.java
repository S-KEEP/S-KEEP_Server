package Skeep.backend.category.service;

import Skeep.backend.category.dto.response.UserCategoryList;
import Skeep.backend.friend.service.FriendRetriever;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCategoryService {
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserFindService userFindService;
    private final FriendRetriever friendRetriever;

    public UserCategoryList getFriendUserCategoryList(
            final Long userId,
            final Long targetId
    ) {
        User currentUser = userFindService.findUserByIdAndStatus(userId);
        User targetUser = userFindService.findUserByIdAndStatus(targetId);

        if (friendRetriever.existsByCrossUserCheck(currentUser, targetUser))
            return userCategoryRetriever.getUserCategoryList(targetId);
        else
            throw BaseException.type(GlobalErrorCode.INTERNAL_SERVER_ERROR);
    }
}
