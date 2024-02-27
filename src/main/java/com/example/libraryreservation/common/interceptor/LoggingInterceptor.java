package com.example.libraryreservation.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.*;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        requestLogging(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("Response. Method: {}, URI: {}, Status: {}", request.getMethod(), request.getRequestURI(), String.valueOf(response.getStatus()));
        MDC.clear();
    }

    public static void requestLogging(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String,String> headers = new HashMap<>();

        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        MDC.put("IP", request.getRemoteAddr());
        MDC.put("Method", request.getMethod());
        MDC.put("URI",request.getRequestURI());
        MDC.put("Headers", headers.toString());

        log.info("Request");
    }
}
