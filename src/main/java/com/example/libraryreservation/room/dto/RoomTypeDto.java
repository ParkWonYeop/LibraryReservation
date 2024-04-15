package com.example.libraryreservation.room.dto;

import com.example.libraryreservation.common.enums.RoomEnum;
import jakarta.validation.constraints.NotNull;

import static com.example.libraryreservation.common.validation.ValidationGroups.NotBlankGroup;

public record RoomTypeDto(
        @NotNull(message = "값이 없습니다.", groups = NotBlankGroup.class)
        RoomEnum roomType
) {
}
