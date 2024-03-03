package com.example.libraryreservation.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class QueryStringFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new RequestWrapper(request), response);
    }

    private static class RequestWrapper extends HttpServletRequestWrapper {
        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String[] getParameterValues(String parameter) {
            String[] values = super.getParameterValues(parameter);

            if (values == null) {
                return super.getParameterValues(toSnakeCase(parameter));
            }

            return values;
        }

        @Override
        public String getParameter(String parameter) {
            String value = super.getParameter(parameter);

            if (value == null) {
                return super.getParameter(toSnakeCase(parameter));
            }

            return value;
        }

        public static String toSnakeCase(String value) {
            String regex = "([a-z])([A-Z]+)";
            String replacement = "$1_$2";
            return value.replaceAll(regex, replacement).toLowerCase();
        }
    }
}
