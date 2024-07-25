package Skeep.backend.user.dto;

import Skeep.backend.user.domain.User;

public interface UserSecurityForm {
    Long getId();

    static UserSecurityForm invoke(User user){
        return new UserSecurityForm() {
            @Override
            public Long getId() {
                return user.getId();
            }
        };
    }
}