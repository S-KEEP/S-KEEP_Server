package Skeep.backend.global.security.handler.logout;

import Skeep.backend.auth.jwt.domain.RefreshToken;
import Skeep.backend.fixture.TokenFixture;
import Skeep.backend.global.ServiceTest;
import Skeep.backend.global.constant.Constants;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.global.security.info.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("[Service Layer] -> CustomLogoutProcessHandler")
class CustomLogoutProcessHandlerTest extends ServiceTest {
    @Autowired
    private CustomLogoutProcessHandler customLogoutProcessHandler;

    private final Long USER_ID = 1L;

    @Nested
    @DisplayName("로그아웃")
    class logout {
        private HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        private HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        private Authentication mockAuthentication = mock(Authentication.class);

        @BeforeEach
        void setUp() {
            refreshTokenRepository.save(RefreshToken.issueRefreshToken(USER_ID, TokenFixture.REFRESH_TOKEN));
        }

        @Test
        void AccessToken이_존재하지_않으면_로그아웃에_실패한다() {
            // when - then
            assertThatThrownBy(() -> customLogoutProcessHandler.logout(mockRequest, mockResponse, null))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(GlobalErrorCode.UNAUTHORIZED.getMessage());
        }

        @Test
        void AccessToken의_형식이_맞지않으면_로그아웃에_실패한다() {
            // given
            when(mockRequest.getHeader(Constants.PREFIX_AUTH)).thenReturn("wrong" + Constants.PREFIX_BEARER + TokenFixture.ACCESS_TOKEN);

            // when
            assertThatThrownBy(() -> customLogoutProcessHandler.logout(mockRequest, mockResponse, mockAuthentication))
                    .isInstanceOf(BaseException.class)
                    .hasMessage(GlobalErrorCode.INVALID_HEADER_VALUE.getMessage());
        }

        @Test
        void 로그아웃에_성공한다() {
            // when
            when(mockRequest.getHeader(Constants.PREFIX_AUTH)).thenReturn(Constants.PREFIX_BEARER + TokenFixture.ACCESS_TOKEN);
            customLogoutProcessHandler.logout(mockRequest, mockResponse, mockAuthentication);

            // then
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(USER_ID);
            assertThat(refreshToken.isEmpty()).isTrue();
        }
    }
}