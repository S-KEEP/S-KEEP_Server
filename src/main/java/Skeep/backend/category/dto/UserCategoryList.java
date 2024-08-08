package Skeep.backend.category.dto;

import Skeep.backend.category.domain.UserCategory;

import java.util.List;
import java.util.stream.Collectors;

public record UserCategoryList (
        List<UserCategoryDto> userCategoryDtoList
){
    private record UserCategoryDto(
            Long id,
            String name,
            String description
    ) {
    }
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