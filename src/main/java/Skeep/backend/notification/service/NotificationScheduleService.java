package Skeep.backend.notification.service;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.location.userLocation.service.UserLocationRetriever;
import Skeep.backend.user.domain.User;
import Skeep.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationScheduleService {
    private final UserFindService userFindService;
    private final UserCategoryRetriever userCategoryRetriever;
    private final UserLocationRetriever userLocationRetriever;

    @Async
    @Scheduled(cron = "0 0 9 * * *")
    public void sendCategoryNotification() {
        List<User> userList = userFindService.findAllByFcmTokenIsNotNull();
        for (User user : userList) {
            List<UserCategory> userCategoryList = userCategoryRetriever.findAllByUser(user);
            List<Map<String, Long>> userCategoryLocationMapList
                    = userCategoryList.stream().map(userCategory -> {
                        Map<String, Long> tempMap = new HashMap<>();
                        Long count = userLocationRetriever.countByUserCategory(userCategory);

                        tempMap.put(userCategory.getName(), count);
                        return tempMap;
                    }).toList();

//            userCategoryLocationMapList.stream().max(
//                    Comparator.comparingLong(obj -> obj.)
//            )



        }


    }


}
