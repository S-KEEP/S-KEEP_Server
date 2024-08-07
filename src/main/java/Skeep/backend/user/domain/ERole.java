package Skeep.backend.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERole {
    USER("USER", "ROLE_USER")
    ;

    private final String role;
    private final String securityRole;
}
