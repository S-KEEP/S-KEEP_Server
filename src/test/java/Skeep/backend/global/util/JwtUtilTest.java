package Skeep.backend.global.util;

import Skeep.backend.global.ServiceTest;
import Skeep.backend.global.constant.Constants;
import Skeep.backend.global.dto.JwtDto;
import Skeep.backend.user.domain.ERole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[Service Layer] -> JwtUtil")
class JwtUtilTest extends ServiceTest {
    @Autowired
    private JwtUtil jwtUtil;

    private final Long USER_ID = 1L;
    private final ERole USER_ROLE = ERole.USER;

    @Test
    void access_token을_생성한다() {
        // when
        String accessToken = jwtUtil.generateAccessToken(USER_ID, USER_ROLE);

        // then
        Long findUserId = jwtUtil.validateToken(accessToken).get(Constants.CLAIM_USER_ID, Long.class);
        assertThat(USER_ID).isEqualTo(findUserId);

        System.out.println(accessToken);
    }

    @Test
    void refresh_token을_생성한다() {
        // when
        String refreshToken = jwtUtil.generateRefreshToken(USER_ID, USER_ROLE);

        // then
        Long findUserId = jwtUtil.validateToken(refreshToken).get(Constants.CLAIM_USER_ID, Long.class);
        assertThat(USER_ID).isEqualTo(findUserId);

        System.out.println(refreshToken);
    }

    @Test
    void JwtDto를_생성한다() {
        // when
        JwtDto jwtDto = jwtUtil.generateTokens(USER_ID, USER_ROLE);

        // then
        Assertions.assertAll(
                () -> assertThat(jwtUtil.validateToken(jwtDto.accessToken()).get(Constants.CLAIM_USER_ID, Long.class)).isEqualTo(USER_ID),
//                () -> assertThat(jwtUtil.validateToken(jwtDto.accessToken()).get(Constants.CLAIM_USER_ROLE, ERole.class)).isEqualTo(USER_ROLE),
                () -> assertThat(jwtUtil.validateToken(jwtDto.refreshToken()).get(Constants.CLAIM_USER_ID, Long.class)).isEqualTo(USER_ID)
//                () -> assertThat(jwtUtil.validateToken(jwtDto.refreshToken()).get(Constants.CLAIM_USER_ROLE, ERole.class)).isEqualTo(USER_ROLE)
                );
    }
}