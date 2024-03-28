package com.example.libraryreservation.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidException(final MethodArgumentNotValidException methodArgumentNotValidException) {
        log.info("asd");
        log.error(methodArgumentNotValidException.getMessage());
        return ResponseEntity.badRequest().body(methodArgumentNotValidException.getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler({
            RuntimeException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<Object> handleBadRequestException(final RuntimeException runtimeException) {
        String errorMessage = runtimeException.getMessage();
        if(errorMessage.contains("Failed to convert value of type") || errorMessage.contains("JSON parse error")) {
            errorMessage = "타입이 잘못되었습니다.";
        }
        log.error(errorMessage);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException accessDeniedException) {
        log.error(accessDeniedException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(accessDeniedException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(LibraryReservationException.class)
    public ResponseEntity<Object> handleLibraryReservationException(final LibraryReservationException libraryReservationException) {
        log.error(libraryReservationException.getMessage());
        return ResponseEntity.status(libraryReservationException.getHttpStatus()).body(libraryReservationException.getMessage());
    }
}
