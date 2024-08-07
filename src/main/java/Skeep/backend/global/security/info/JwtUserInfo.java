package Skeep.backend.global.security.info;

import Skeep.backend.user.domain.ERole;

public record JwtUserInfo(Long userId, ERole role) {
}
