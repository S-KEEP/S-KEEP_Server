package Skeep.backend.auth.apple.controller;

import Skeep.backend.auth.apple.dto.AppleLoginRequest;
import Skeep.backend.fixture.TokenFixture;
import Skeep.backend.global.ControllerTest;
import Skeep.backend.global.dto.JwtDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[Controller Layer] -> AppleController")
class AppleControllerTest extends ControllerTest {
    @Nested
    @DisplayName("로그인 API [POST /api/auth/apple/login]")
    class login {
        private static final String BASE_URL = "/api/auth/apple/login";

        @Test
        void 이미_있는_사용자의_로그인을_성공하다() throws Exception {
            // given
            JwtDto jwtDto = new JwtDto(TokenFixture.ACCESS_TOKEN, TokenFixture.REFRESH_TOKEN);
            doReturn(jwtDto).when(appleService).login(any());

            // when
            MockHttpServletRequestBuilder requestBuilder = post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new AppleLoginRequest(
                            "test",
                            "c50d317be38c742c0beb19d8743de014c.0.nruy.1NtQvAmp9uhyrsMj1mp7kg",
                            "eyJraWQiOiI4NkQ4OEtmIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwczovL2FwcGxlaWQuYXBwbGUuY29tIiwiYXVkIjoiY29tLndoaXRlcGFlay5zZXJ2aWNlcyIsImV4cCI6MTU5ODgwMDEyOCwiaWF0IjoxNTk4Nzk5NTI4LCJzdWIiOiIwMDAxNDguZjA2ZDgyMmNlMGIyNDgzYWFhOTdkMjczYjA5NzgzMjUuMTcxNyIsIm5vbmNlIjoiMjBCMjBELTBTOC0xSzgiLCJjX2hhc2giOiJ1aFFiV0gzQUFWdEc1OUw4eEpTMldRIiwiZW1haWwiOiJpNzlmaWl0OWIzQHByaXZhdGVyZWxheS5hcHBsZWlkLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjoidHJ1ZSIsImlzX3ByaXZhdGVfZW1haWwiOiJ0cnVlIiwiYXV0aF90aW1lIjoxNTk4Nzk5NTI4LCJub25jZV9zdXBwb3J0ZWQiOnRydWV9.GQBCUHza0yttOfpQ-J5OvyZoGe5Zny8pI06sKVDIJaQY3bdiphllg1_pHMtPUp7FLv3ccthcmqmZn7NWVoIPkc9-_8squ_fp9F68XM-UsERKVzBvVR92TwQuKOPFr4lRn-2FlBzN4NegicMS-IV8Ad3AKTIRMIhvAXG4UgNxgPAuCpHwCwEAJijljfUfnRYO-_ywgTcF26szluBz9w0Y1nn_IIVCUzAwYiEMdLo53NoyJmWYFWu8pxmXRpunbMHl5nvFpf9nK-OGtMJrmZ4DlpTc2Gv64Zs2bwHDEvOyQ1WiRUB6_FWRH5FV10JSsccMlm6iOByOLYd03RRH2uYtFw",
                            new AppleLoginRequest.AppleUser("abcabc@icloud.com", new AppleLoginRequest.AppleUser.AppleName("Chaerin", "Yang"))
                    )));

            // then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.accessToken").value(jwtDto.accessToken()))
                    .andExpect(jsonPath("$.refreshToken").value(jwtDto.refreshToken()))
                    .andDo(print());
        }
    }
}