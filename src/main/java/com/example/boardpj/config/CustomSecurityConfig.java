package com.example.boardpj.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class CustomSecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)

                //  URL 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/member/join",
                                "/member/login",
                                "/board/list",
                                "/member/check-id",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated());

        http.formLogin(form -> {
            form.loginPage("/member/login")
                    .loginProcessingUrl("/member/login")
                    .usernameParameter("userId")
                    .passwordParameter("userPw")
                    .defaultSuccessUrl("/board/list")
                    .failureUrl("/member/login");
        })

                //  로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/member/login")
                        .invalidateHttpSession(true)    //  세션 무효화
                        .deleteCookies("JSESSIONID", "remember-me")     //  쿠키 삭제
                        .clearAuthentication(true))     //  인증 정보 제거

                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(3600)     //  30분
                        .userDetailsService(userDetailsService)
                )

                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false));

        return http.build();
    }
}
