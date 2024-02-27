package com.example.libraryreservation.common.jwt;

import com.example.libraryreservation.common.enums.JwtErrorEnum;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.libraryreservation.common.interceptor.LoggingInterceptor.requestLogging;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtErrorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try{
            requestLogging(request);
            filterChain.doFilter(request,response);
            MDC.clear();
        } catch (JwtException | ServletException e) {
            String message = e.getMessage();

            log.error(message);

            if(JwtErrorEnum.UNKNOWN_ERROR.getMsg().equals(message)) {
                setResponse(request, response, JwtErrorEnum.UNKNOWN_ERROR);
            }
            else if(JwtErrorEnum.WRONG_TYPE_TOKEN.getMsg().equals(message)) {
                setResponse(request, response, JwtErrorEnum.WRONG_TYPE_TOKEN);
            }
            else if(JwtErrorEnum.EXPIRED_TOKEN.getMsg().equals(message)) {
                setResponse(request, response, JwtErrorEnum.EXPIRED_TOKEN);
            }
            else if(JwtErrorEnum.UNSUPPORTED_TOKEN.getMsg().equals(message)) {
                setResponse(request, response, JwtErrorEnum.UNSUPPORTED_TOKEN);
            }
            else if(JwtErrorEnum.NULL_PARAM.getMsg().equals(message)) {
                setResponse(request, response, JwtErrorEnum.NULL_PARAM);
            }
            else {
                setResponse(request, response, JwtErrorEnum.ACCESS_DENIED);
            }
            MDC.clear();
        }
    }

    private void setResponse(HttpServletRequest request,HttpServletResponse response, JwtErrorEnum errorMessage) throws RuntimeException, IOException {
        log.info("Response. Method: {}, URI: {}, Status: {}, Message: {}", request.getMethod(), request.getRequestURI(), String.valueOf(errorMessage.getCode()), errorMessage.getMsg());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorMessage.getCode());
        response.getWriter().print(errorMessage.getMsg());
    }
}
