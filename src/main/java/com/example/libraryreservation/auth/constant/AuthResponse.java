package com.example.libraryreservation.auth.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthResponse {
    ALREADY_SIGNUP_PHONENUMBER(HttpStatus.BAD_REQUEST, "이미 가입한 전화번호입니다.");

    final HttpStatus httpStatus;
    final String message;
}
