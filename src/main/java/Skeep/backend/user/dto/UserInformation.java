package Skeep.backend.user.dto;

import Skeep.backend.user.domain.User;

public record UserInformation(
        String name,
        String email,
        String provider
){
    public static UserInformation toUserInformation(User user) {
        return new UserInformation(user.getName(), user.getEmail().getEmail(), user.getProvider().getName());
    }
}
