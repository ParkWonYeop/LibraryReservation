package com.example.libraryreservation.common.controller;

import com.example.libraryreservation.admin.constant.AdminResponse;
import com.example.libraryreservation.auth.constant.AuthResponse;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.reservation.constant.ReservationResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LibraryReservationException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public LibraryReservationException(AdminResponse adminResponse) {
        this.httpStatus = adminResponse.getHttpStatus();
        this.message = adminResponse.getMessage();
    }

    public LibraryReservationException(AuthResponse authResponse) {
        this.httpStatus = authResponse.getHttpStatus();
        this.message = authResponse.getMessage();
    }

    public LibraryReservationException(ReservationResponse reservationResponse) {
        this.httpStatus = reservationResponse.getHttpStatus();
        this.message = reservationResponse.getMessage();
    }

    public LibraryReservationException(CommunalResponse communalResponse) {
        this.httpStatus = communalResponse.getHttpStatus();
        this.message = communalResponse.getMessage();
    }
}
