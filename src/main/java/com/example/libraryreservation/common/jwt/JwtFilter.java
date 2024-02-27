package com.example.libraryreservation.common.jwt;

import com.example.libraryreservation.common.model.TokenModel;
import com.example.libraryreservation.common.model.UserModel;
import com.example.libraryreservation.common.repository.TokenRepository;
import com.example.libraryreservation.common.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;


@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final String secretKey;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public JwtFilter(String secretKey, UserRepository userRepository, TokenRepository tokenRepository) {
        this.secretKey = secretKey;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getRequestURI().contains("/auth")) {
            final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                log.error("authorization 이 없습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorization.split(" ")[1];

            if (JwtUtil.isExpired(token, secretKey)) {
                log.error("Token이 만료되었습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            String phoneNumber = JwtUtil.getSubject(token, secretKey);

            if (phoneNumber == null) {
                log.error("토큰에 Subject가 없습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(phoneNumber);

            if (userModel.isEmpty()) {
                log.error("유저를 찾을 수 없습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            Optional<TokenModel> tokenModel = tokenRepository.findTokenModelByUserModel(userModel.get());

            if (tokenModel.isPresent()&&!Objects.equals(tokenModel.get().getAccessToken(), token)) {
                log.error("토큰이 유효하지 않습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            MDC.put("phoneNumber", phoneNumber);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, null, List.of(new SimpleGrantedAuthority("ROLE_" + userModel.get().getPermission().toString())));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
        MDC.clear();
    }
}
