package Skeep.backend.global.security.config;

import Skeep.backend.auth.jwt.domain.RefreshTokenRepository;
import Skeep.backend.global.constant.Constants;
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
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Bean
    public CustomUserDetailService customUserDetailService() {
        return new CustomUserDetailService(userRepository);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, jwtAuthenticationManager());
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler() {
        return new CustomAuthenticationEntryPointHandler();
    }

    @Bean
    public CustomLogoutProcessHandler customLogoutProcessHandler() {
        return new CustomLogoutProcessHandler(refreshTokenRepository, jwtUtil);
    }

    @Bean
    public CustomLogoutResultHandler customLogoutResultHandler() {
        return new CustomLogoutResultHandler();
    }

    @Bean
    public JwtAuthenticationManager jwtAuthenticationManager() {
        return new JwtAuthenticationManager(jwtAuthenticationProvider());
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(customUserDetailService());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.httpBasic(AbstractHttpConfigurer::disable);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(request ->
                request
                        .requestMatchers(Constants.NO_NEED_AUTH.toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated()
        );

        http.formLogin(AbstractHttpConfigurer::disable);

        http.logout(logout ->
                logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(customLogoutProcessHandler())
                        .logoutSuccessHandler(customLogoutResultHandler())
        );

        http.exceptionHandling(exception ->
                exception
                        .accessDeniedHandler(customAccessDeniedHandler())
                        .authenticationEntryPoint(customAuthenticationEntryPointHandler())
        );

        http.addFilterBefore(
                new JwtAuthenticationFilter(jwtUtil, jwtAuthenticationManager()), LogoutFilter.class
        );
        http.addFilterBefore(
                new JwtExceptionFilter(), JwtAuthenticationFilter.class
        );

        return http.build();
    }
}