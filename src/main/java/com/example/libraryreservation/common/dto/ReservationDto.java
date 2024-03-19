package com.example.libraryreservation.common.dto;

import com.example.libraryreservation.common.enums.RoomEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationDto(
        @NotNull(message = "빈 문자열 입니다.")
        RoomEnum roomType,
        @NotNull(message = "빈 문자열 입니다.")
        Integer seatNumber,
        @NotNull(message = "빈 문자열 입니다.")
        @Future(message = "현재보다 과거입니다.")
        LocalDateTime startTime,
        @NotNull(message = "빈 문자열 입니다.")
        @Future(message = "현재보다 과거입니다.")
        LocalDateTime endTime
) {
}
