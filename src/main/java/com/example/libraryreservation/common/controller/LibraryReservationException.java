package com.example.libraryreservation.common.controller;

import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LibraryReservationException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public LibraryReservationException(CommunalResponse communalResponse) {
        this.httpStatus = communalResponse.getHttpStatus();
        this.message = communalResponse.getMessage();
    }
}