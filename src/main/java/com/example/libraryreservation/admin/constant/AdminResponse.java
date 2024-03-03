package com.example.libraryreservation.admin.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AdminResponse {
    NOT_EXIST_RESERVATION(HttpStatus.BAD_REQUEST, "존재하지 않는 예약");

    final HttpStatus httpStatus;
    final String message;
}
