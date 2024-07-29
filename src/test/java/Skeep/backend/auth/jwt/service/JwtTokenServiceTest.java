package Skeep.backend.auth.jwt.service;

import Skeep.backend.auth.exception.AuthErrorCode;
import Skeep.backend.auth.jwt.domain.RefreshToken;
import Skeep.backend.fixture.TokenFixture;
import Skeep.backend.global.ServiceTest;
import Skeep.backend.global.constant.Constants;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[Service Layer] -> JwtTokenService")
class JwtTokenServiceTest extends ServiceTest {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    private final Long USER_ID = 1L;

    @Nested
    @DisplayName("AccessToken 재발급")
    class reissueAccessToken {
        @BeforeEach
        void setUp() {
            refreshTokenRepository.save(RefreshToken.issueRefreshToken(USER_ID, TokenFixture.REFRESH_TOKEN));
        }

        @Test
        void RefreshToken이_유효하지_않으면_재발급에_실패한다() {
            String wrongRefreshToken = jwtUtil.generateRefreshToken(USER_ID);

            assertThatThrownBy(() -> jwtTokenService.reissueAccessToken(wrongRefreshToken))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(AuthErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        @Test
        void AccessToken_재발급에_성공한다() {
            // when
            String newAccessToken = jwtTokenService.reissueAccessToken(TokenFixture.REFRESH_TOKEN);

            // then
            Long findUserId = jwtUtil.validateToken(newAccessToken).get(Constants.CLAIM_USER_ID, Long.class);
            assertThat(findUserId).isEqualTo(USER_ID);
        }
    }

    @Nested
    @DisplayName("RefreshToken 레디스 업데이트")
    class updateRefreshToken {
        @Test
        void RefreshToken_보유자에게_새로운_RefreshToken으로_업데이트한다() {
            // given
            refreshTokenRepository.save(RefreshToken.issueRefreshToken(USER_ID, TokenFixture.REFRESH_TOKEN));

            // when
            String newRefreshToken = jwtUtil.generateRefreshToken(USER_ID);
            jwtTokenService.updateRefreshToken(USER_ID, newRefreshToken);

            // then
            Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findById(USER_ID);
            assertAll(
                    () -> assertThat(findRefreshToken.get().getId()).isEqualTo(USER_ID),
                    () -> assertThat(findRefreshToken.get().getToken()).isEqualTo(newRefreshToken)
            );
        }

        @Test
        void RefreshToken_보유하고_있지_않은_유저_에게_새로운_RefreshToken으로_업데이트한다() {
            // when
            jwtTokenService.updateRefreshToken(USER_ID, TokenFixture.REFRESH_TOKEN);

            // then
            Optional<RefreshToken> findRefreshToken = refreshTokenRepository.findById(USER_ID);
            assertAll(
                    () -> assertThat(findRefreshToken.get().getId()).isEqualTo(USER_ID),
                    () -> assertThat(findRefreshToken.get().getToken()).isEqualTo(TokenFixture.REFRESH_TOKEN)
            );
        }
    }
}