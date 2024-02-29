package com.example.libraryreservation.admin.constant;

import org.springframework.http.HttpStatus;

public enum AdminResponse {
    NOT_EXIST_RESERVATION(HttpStatus.BAD_REQUEST, "존재하지 않는 예약")
}
