package com.example.libraryreservation.common.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommunalResponse {
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "유저를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 방입니다.");

    final HttpStatus httpStatus;
    final String message;
}

