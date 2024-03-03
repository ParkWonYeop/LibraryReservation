package com.example.libraryreservation.reservation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ReservationResponse {
    SEAT_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 좌석 번호 입니다."),
    ALREADY_RESERVATION_USER(HttpStatus.BAD_REQUEST, "이미 예약한 유저입니다."),
    ALREADY_RESERVATION_SEAT(HttpStatus.BAD_REQUEST, "이미 예약된 좌석입니다."),
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "예약을 찾을 수 없습니다."),
    USER_NOT_CORRECT(HttpStatus.BAD_REQUEST, "유저가 일치 하지 않습니다.");

    final HttpStatus httpStatus;
    final String message;
}

