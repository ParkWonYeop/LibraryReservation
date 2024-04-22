package com.example.libraryreservation.common.dto;

import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.validation.enumValidation.EnumValid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.example.libraryreservation.common.validation.ValidationGroups.*;

public record ReservationDto(
        @EnumValid(enumClass = RoomEnum.class, groups = PatternGroup.class)
        @NotNull(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        String roomType,
        @NotNull(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        Integer seatNumber,
        @NotNull(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        @Future(message = "현재보다 과거입니다.", groups = FutureGroup.class)
        LocalDateTime startTime,
        @NotNull(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        @Future(message = "현재보다 과거입니다.", groups = FutureGroup.class)
        LocalDateTime endTime
) {
}
