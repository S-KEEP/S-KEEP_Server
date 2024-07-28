package Skeep.backend.auth.jwt.controller;

import Skeep.backend.global.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("[Controller Layer] -> TokenReissueApiController")
class TokenReissueApiControllerTest extends ControllerTest {
    @Nested
    @DisplayName("AccessToken 재발급 API 테스트 [POST /api/auth/jwt/reissue]")
    class reissueToken {
        private static final String BASE_URL = "/api/auth/jwt/reissue";
        private static final String NEW_ACCESS_TOKEN = "eyJKV1QiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1dWlkIjoxLCJpYXQiOjE3MjIxODkyOTcsImV4cCI6MTcyMjI3NTY5N30.-HS_QUQeEL9AG9c2ASp8urUP7eijg6BWKz73A6gA_dTED3h3m_QkpS_NeFEsILKcLxHzpNV7Tysb-EP8BNM8xg";

        @Test
        void 리프레시_토큰이_유효하지_않으면_예외가_발생한다() throws Exception {
        }
    }
}