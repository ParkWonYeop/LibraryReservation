package com.example.libraryreservation.jwt;

import com.example.libraryreservation.auth.AuthService;
import com.example.libraryreservation.model.UserModel;
import com.example.libraryreservation.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final String secretKey;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> headers = new ArrayList<>();

        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add("\nHeaderName: {"+headerName+"}, Values: {"+request.getHeader(headerName)+"}");
        }

        logger.info("Request received. Method: {"+request.getMethod()+"}, URI: {"+request.getRequestURI()+"}, IP: {"+request.getRemoteAddr()+"},\nHeaders: {"+headers+"\n}");

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            logger.error("authorization 이 없습니다.");
            filterChain.doFilter(request, response);
            return ;
        }

        String token = authorization.split(" ")[1];

        if(JwtUtil.isExpired(token, secretKey)) {
            logger.error("Token이 만료되었습니다.");
            filterChain.doFilter(request,response);
            return ;
        }

        String phoneNumber = JwtUtil.getSubject(token, secretKey);

        if(phoneNumber == null) {
            logger.error("토큰에 Subject가 없습니다.");
            filterChain.doFilter(request,response);
            return ;
        }

        Optional<UserModel> userModel = userRepository.findUserModelByPhoneNumber(phoneNumber);

        if(userModel.isEmpty()) {
            logger.error("유저를 찾을 수 없습니다.");
            filterChain.doFilter(request,response);
            return ;
        }

        if(!Objects.equals(userModel.get().getTokenModel().getAccessToken(), token)) {
            logger.error("토큰이 유효하지 않습니다.");
            filterChain.doFilter(request,response);
            return ;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, null, List.of(new SimpleGrantedAuthority("ROLE_"+userModel.get().getPermission().toString())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
