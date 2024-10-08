package Skeep.backend.global;

import Skeep.backend.auth.apple.controller.AppleController;
import Skeep.backend.auth.apple.service.AppleOAuthManager;
import Skeep.backend.auth.apple.service.ApplePublicKeyClient;
import Skeep.backend.auth.apple.service.AppleService;
import Skeep.backend.auth.apple.service.AppleTokenUtil;
import Skeep.backend.auth.jwt.controller.TokenReissueApiController;
import Skeep.backend.auth.jwt.domain.RefreshTokenRepository;
import Skeep.backend.auth.jwt.service.JwtTokenService;
import Skeep.backend.category.controller.UserCategoryController;
import Skeep.backend.category.service.UserCategoryRemover;
import Skeep.backend.category.service.UserCategoryRetriever;
import Skeep.backend.category.service.UserCategorySaver;
import Skeep.backend.category.service.UserCategoryUpdater;
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
import Skeep.backend.location.userLocation.service.UserLocationRemover;
import Skeep.backend.user.controller.UserController;
import Skeep.backend.user.domain.UserRepository;
import Skeep.backend.user.service.UserFindService;
import Skeep.backend.user.service.UserService;
import Skeep.backend.user.service.UserWithdrawalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(value = {
        AppleController.class,
        TokenReissueApiController.class,
        UserController.class,
        UserCategoryController.class,
})
@AutoConfigureMockMvc
@Import({SecurityConfig.class})
@ExtendWith(SpringExtension.class)
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
    protected UserService userService;

    @MockBean
    protected UserFindService userFindService;

    @MockBean
    protected UserWithdrawalService userWithdrawalService;

    @MockBean
    protected AppleService appleService;

    @MockBean
    protected ApplePublicKeyClient applePublicKeyClient;

    @MockBean
    protected AppleOAuthManager appleOAuthManager;

    @MockBean
    protected JwtTokenService jwtTokenService;

    @MockBean
    protected UserCategoryRetriever userCategoryRetriever;

    @MockBean
    protected UserCategoryUpdater userCategoryUpdater;

    @MockBean
    protected UserCategoryRemover userCategoryRemover;

    @MockBean
    protected UserCategorySaver userCategorySaver;

    @MockBean
    protected UserLocationRemover userLocationRemover;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
