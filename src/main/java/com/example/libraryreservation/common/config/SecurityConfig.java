package com.example.libraryreservation.common.config;

import com.example.libraryreservation.common.enums.PermissionEnum;
import com.example.libraryreservation.common.filter.QueryStringFilter;
import com.example.libraryreservation.common.jwt.JwtErrorFilter;
import com.example.libraryreservation.common.jwt.JwtFilter;
import com.example.libraryreservation.common.repository.TokenRepository;
import com.example.libraryreservation.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret_key}")
    private String secretKey;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/login", "/auth/signup", "/auth/test").permitAll();
                    requests.requestMatchers(HttpMethod.PUT, "/auth/token").permitAll();
                    requests.requestMatchers("/h2-console/*").permitAll();
                    requests.requestMatchers(HttpMethod.GET, "/auth/token").authenticated();
                    requests.requestMatchers("/room/**", "/reservation/**").authenticated();
                    requests.requestMatchers("/admin/**").hasAnyRole(PermissionEnum.ADMIN.toString());
                })
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtFilter(secretKey, userRepository, tokenRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new QueryStringFilter(), JwtFilter.class)
                .addFilterBefore(new JwtErrorFilter(), QueryStringFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
