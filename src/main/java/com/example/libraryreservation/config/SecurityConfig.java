package com.example.libraryreservation.config;

import com.example.libraryreservation.auth.AuthService;
import com.example.libraryreservation.enums.JwtErrorEnum;
import com.example.libraryreservation.enums.PermissionEnum;
import com.example.libraryreservation.filter.QueryStringFilter;
import com.example.libraryreservation.jwt.JwtErrorFilter;
import com.example.libraryreservation.jwt.JwtFilter;
import com.example.libraryreservation.jwt.JwtUtil;
import com.example.libraryreservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${jwt.secret_key}")
    private String secretKey;
    private final UserRepository userRepository;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/login", "/auth/signup","/auth/test").permitAll();
                    requests.requestMatchers(HttpMethod.PUT, "/auth/token").permitAll();
                    requests.requestMatchers(HttpMethod.GET, "/auth/token").authenticated();
                    requests.requestMatchers("/room/**", "/reservation/**").authenticated();
                    requests.requestMatchers("/admin/**").hasAnyRole(PermissionEnum.ADMIN.toString());
                })
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtFilter(secretKey, userRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new QueryStringFilter(), JwtFilter.class)
                .addFilterBefore(new JwtErrorFilter(), QueryStringFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
