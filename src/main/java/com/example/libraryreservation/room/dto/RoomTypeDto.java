package com.example.libraryreservation.room.dto;

import com.example.libraryreservation.common.enums.RoomEnum;
import jakarta.validation.constraints.NotNull;

public record RoomTypeDto(
        @NotNull(message = "값이 없습니다.")
        RoomEnum roomType
) {
}
