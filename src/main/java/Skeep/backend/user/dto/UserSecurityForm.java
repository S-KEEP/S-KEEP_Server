package Skeep.backend.user.dto;

import Skeep.backend.user.domain.ERole;
import Skeep.backend.user.domain.User;

public interface UserSecurityForm {
    Long getId();
    ERole getRole();

    static UserSecurityForm invoke(User user){
        return new UserSecurityForm() {
            @Override
            public Long getId() {
                return user.getId();
            }

            @Override
            public ERole getRole() {
                return user.getRole();
            }
        };
    }
}