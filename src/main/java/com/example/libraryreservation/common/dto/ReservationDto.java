package com.example.libraryreservation.common.dto;

import com.example.libraryreservation.common.enums.RoomEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {
    @NotBlank(message = "빈 문자열 입니다.")
    private RoomEnum roomType;
    @NotBlank(message = "빈 문자열 입니다.")
    private Integer seatNumber;
    @NotBlank(message = "빈 문자열 입니다.")
    @Future(message = "현재보다 과거입니다.")
    private LocalDateTime startTime;
    @NotBlank(message = "빈 문자열 입니다.")
    @Future(message = "현재보다 과거입니다.")
    private LocalDateTime endTime;
}
