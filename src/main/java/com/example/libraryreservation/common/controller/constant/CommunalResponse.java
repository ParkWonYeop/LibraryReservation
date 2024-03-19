package com.example.libraryreservation.common.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommunalResponse {
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "유저를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 방입니다."),
    //ReservationResponse
    SEAT_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 좌석 번호 입니다."),
    ALREADY_RESERVATION_USER(HttpStatus.BAD_REQUEST, "이미 예약한 유저입니다."),
    ALREADY_RESERVATION_SEAT(HttpStatus.BAD_REQUEST, "이미 예약된 좌석입니다."),
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "예약을 찾을 수 없습니다."),
    USER_NOT_CORRECT(HttpStatus.BAD_REQUEST, "유저가 일치 하지 않습니다."),
    //AuthResponse
    ALREADY_SIGNUP_PHONENUMBER(HttpStatus.BAD_REQUEST, "이미 가입한 전화번호입니다.");

    final HttpStatus httpStatus;
    final String message;
}

