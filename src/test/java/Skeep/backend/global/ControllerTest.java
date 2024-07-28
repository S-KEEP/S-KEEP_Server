package Skeep.backend.global;

import Skeep.backend.auth.apple.controller.AppleController;
import Skeep.backend.auth.apple.service.AppleAuthClient;
import Skeep.backend.auth.apple.service.ApplePublicKeyGenerator;
import Skeep.backend.auth.apple.service.AppleService;
import Skeep.backend.auth.apple.service.AppleTokenUtil;
import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.global.security.config.SecurityConfig;
import Skeep.backend.global.security.filter.JwtAuthenticationFilter;
import Skeep.backend.global.security.filter.JwtExceptionFilter;
import Skeep.backend.global.security.handler.exception.CustomAccessDeniedHandler;
import Skeep.backend.global.security.handler.exception.CustomAuthenticationEntryPointHandler;
import Skeep.backend.global.security.handler.logout.CustomLogoutProcessHandler;
import Skeep.backend.global.security.handler.logout.CustomLogoutResultHandler;
import Skeep.backend.global.security.provider.JwtAuthenticationManager;
import Skeep.backend.global.security.provider.JwtAuthenticationProvider;
import Skeep.backend.global.security.service.CustomUserDetailService;
import Skeep.backend.global.util.JwtUtil;
import Skeep.backend.user.domain.UserRepository;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = {
        AppleController.class
})
@Import({SecurityConfig.class})
public abstract class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    protected JwtExceptionFilter jwtExceptionFilter;

    @MockBean
    protected CustomAccessDeniedHandler customAccessDeniedHandler;

    @MockBean
    protected CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;

    @MockBean
    protected CustomLogoutProcessHandler customLogoutProcessHandler;

    @MockBean
    protected CustomLogoutResultHandler customLogoutResultHandler;

    @MockBean
    protected JwtAuthenticationManager jwtAuthenticationManager;

    @MockBean
    protected JwtAuthenticationProvider jwtAuthenticationProvider;

    @MockBean
    protected CustomUserDetailService customUserDetailService;

    @MockBean
    protected JwtUtil jwtUtil;

    @MockBean
    protected AppleTokenUtil appleTokenUtil;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserFindService userFindService;

    @MockBean
    protected AppleService appleService;

    @MockBean
    protected AppleAuthClient appleAuthClient;

    @MockBean
    protected ApplePublicKeyGenerator applePublicKeyGenerator;

    @MockBean
    protected JwtTokenService jwtTokenService;
}
