package com.example.libraryreservation.annotation;

import com.example.libraryreservation.enums.JwtErrorEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class QueryStringNamingResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(QueryStringNaming.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws ServletException {
        QueryStringNaming annotation = parameter.getParameterAnnotation(QueryStringNaming.class);
        String annotationValue = Objects.requireNonNull(annotation).value();
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if(parameterMap.isEmpty()){
            throw new ServletException(JwtErrorEnum.NULL_PARAM.getMsg());
        }

        String result = "";

        for (String paramKey : parameterMap.keySet()) {
            String convertValue = toCamelCase(paramKey);
            log.info(convertValue);
            log.info(annotationValue);
            if(convertValue.equals(annotationValue)) {
                result = parameterMap.get(paramKey)[0];
                log.info(result);
            }
        }

        if(Objects.equals(result, "")) {
            throw new ServletException(JwtErrorEnum.NULL_PARAM.getMsg());
        }

        return result;
    }

    private String toCamelCase(String value) {
        String[] parts = value.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0]);

        for(int i = 1; i< parts.length; i++) {
            camelCaseString.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
        }

        return camelCaseString.toString();
    }
}
