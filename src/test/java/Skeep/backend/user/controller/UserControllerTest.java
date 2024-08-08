package Skeep.backend.user.controller;

import Skeep.backend.global.ControllerTest;
import Skeep.backend.global.exception.BaseException;
import Skeep.backend.global.exception.GlobalErrorCode;
import Skeep.backend.user.domain.EProvider;
import Skeep.backend.user.exception.UserErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static Skeep.backend.fixture.TokenFixture.ACCESS_TOKEN;
import static Skeep.backend.fixture.UserFixture.ALICE_JOHNSON;
import static Skeep.backend.global.constant.Constants.PREFIX_AUTH;
import static Skeep.backend.global.constant.Constants.PREFIX_BEARER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Controller Layer] -> UserController")
class UserControllerTest extends ControllerTest {
    @Nested
    @DisplayName("유저 정보 조회 API 테스트 [GET /api/user]")
    class getUserInformation {
        private static final String BASE_URL = "/api/user";

        @Test
        void 토큰이_없다면_예외가_발생한다() throws Exception {
            // when
            MockHttpServletRequestBuilder requestBuilder = get(BASE_URL);

            // then
            final GlobalErrorCode expectedError = GlobalErrorCode.INVALID_HEADER_VALUE;
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof BaseException))
                    .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), expectedError.getMessage()))
                    .andDo(print());
        }

        @Test
        void 유저_정보를_찾을_수_없다면_예외가_발생한다() throws Exception {
            // given
            doThrow(BaseException.type(UserErrorCode.NOT_FOUND_USER))
                    .when(userFindService)
                    .findById(anyLong());

            // when
            MockHttpServletRequestBuilder requestBuilder = get(BASE_URL)
                    .header(PREFIX_AUTH, PREFIX_BEARER + ACCESS_TOKEN);

            // then
            final UserErrorCode expectedError = UserErrorCode.NOT_FOUND_USER;
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof BaseException))
                    .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), expectedError.getMessage()))
                    .andDo(print());
        }

        @Test
        void 유저_정보_조회에_성공한다() throws Exception {
            // given
            given(userFindService.findById(anyLong()))
                    .willReturn(ALICE_JOHNSON.toUser(EProvider.APPLE));

            // when
            MockHttpServletRequestBuilder requestBuilder = get(BASE_URL)
                    .header(PREFIX_AUTH, PREFIX_BEARER + ACCESS_TOKEN);

            // then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.user.name").value(ALICE_JOHNSON.getName()))
                    .andExpect(jsonPath("$.user.email").value(ALICE_JOHNSON.getEmail().getEmail()))
                    .andExpect(jsonPath("$.user.provider").value(ALICE_JOHNSON.getProvider().getName()));
        }
    }

    @Nested
    @DisplayName("서비스 회원 탈퇴 API [POST /api/user/withdrawal]")
    class withdrawalUser {
        private static final String BASE_URL = "/api/user/withdrawal";

        @Test
        void 유저_정보를_찾을_수_없다면_예외가_발생한다() throws Exception {
            // given
            doThrow(BaseException.type(UserErrorCode.NOT_FOUND_USER))
                    .when(userFindService)
                    .findById(anyLong());

            // when
            MockHttpServletRequestBuilder requestBuilder = post(BASE_URL)
                    .header(PREFIX_AUTH, PREFIX_BEARER + ACCESS_TOKEN);

            // then
            final UserErrorCode expectedError = UserErrorCode.NOT_FOUND_USER;
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof BaseException))
                    .andExpect(result -> assertEquals(result.getResolvedException().getMessage(), expectedError.getMessage()))
                    .andDo(print());
        }

        @Test
        void 서비스_회원_탈퇴에_성공한다() throws Exception {
            // given
            doNothing()
                    .when(userService)
                    .withdrawalUser(anyLong());

            // when
            MockHttpServletRequestBuilder requestBuilder = post(BASE_URL)
                    .header(PREFIX_AUTH, PREFIX_BEARER + ACCESS_TOKEN);

            // then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
        }
    }
}