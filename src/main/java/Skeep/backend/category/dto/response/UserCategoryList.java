package Skeep.backend.category.dto.response;

import Skeep.backend.category.domain.UserCategory;
import Skeep.backend.category.dto.UserCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

public record UserCategoryList (
        List<UserCategoryDto> userCategoryDtoList
){
    public static UserCategoryList createDto(List<UserCategory> userCategoryList) {
        List<UserCategoryDto> list = userCategoryList.stream()
                .map(userCategory -> new UserCategoryDto(
                        userCategory.getId(),
                        userCategory.getName(),
                        userCategory.getDescription()
                ))
                .collect(Collectors.toList());

        return new UserCategoryList(list);
    }
}