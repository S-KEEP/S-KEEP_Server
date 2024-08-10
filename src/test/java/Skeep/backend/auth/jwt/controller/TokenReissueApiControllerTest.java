package Skeep.backend.auth.jwt.controller;

import Skeep.backend.auth.exception.AuthErrorCode;
import Skeep.backend.auth.jwt.dto.RefreshTokenRequest;
import Skeep.backend.fixture.TokenFixture;
import Skeep.backend.global.ControllerTest;
import Skeep.backend.global.exception.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Controller Layer] -> TokenReissueApiController")
class TokenReissueApiControllerTest extends ControllerTest {
    @Nested
    @DisplayName("AccessToken 재발급 API 테스트 [POST /api/auth/jwt/reissue]")
    @WithAnonymousUser
    class reissueToken {
        private static final String BASE_URL = "/api/auth/jwt/reissue";
        private static final String NEW_ACCESS_TOKEN = "eyJKV1QiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1dWlkIjoxLCJpYXQiOjE3MjIxODkyOTcsImV4cCI6MTcyMjI3NTY5N30.-HS_QUQeEL9AG9c2ASp8urUP7eijg6BWKz73A6gA_dTED3h3m_QkpS_NeFEsILKcLxHzpNV7Tysb-EP8BNM8xg";
        private static final String WRONG_REFRESH_TOKEN = "eyJKV1QiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1dWlkIjoxLCJpYXQiOjE3MjIxODkyOTcsImV4cCI6MTcyMjI3NTY5N30.-HS_QUQeEL9AG9c2ASp8urUP7eijg6BWKz73A6gA_dTED3h3m_QkpS_NeFEsILKcLxHzpNV7Tysb-EP8BNM8xg";

        @Test
        void 레디스에서_리프레시_토큰을_찾을_수_없으면_예외가_발생한다() throws Exception {
            // given
            doThrow(BaseException.type(AuthErrorCode.INVALID_REFRESH_TOKEN))
                    .when(jwtTokenService)
                    .reissueAccessToken(WRONG_REFRESH_TOKEN);

            // when
            MockHttpServletRequestBuilder requestBuilder = post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new RefreshTokenRequest(WRONG_REFRESH_TOKEN)));

            // then
            final AuthErrorCode expectedError = AuthErrorCode.INVALID_REFRESH_TOKEN;
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isUnauthorized())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof BaseException))
                    .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), expectedError.getMessage()))
                    .andDo(print());
        }

        @Test
        void 리프레시_토큰으로_액세스_토큰_재발급에_성공한다() throws Exception {
            // given
            given(jwtTokenService.reissueAccessToken(anyString()))
                    .willReturn(NEW_ACCESS_TOKEN);

            // when
            MockHttpServletRequestBuilder requestBuilder = post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new RefreshTokenRequest(TokenFixture.REFRESH_TOKEN)));

            // then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result.accessToken").value(NEW_ACCESS_TOKEN))
                    .andDo(print());
        }
    }
}