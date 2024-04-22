package com.example.libraryreservation.room.dto;

import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.validation.enumValidation.EnumValid;
import jakarta.validation.constraints.NotNull;

import static com.example.libraryreservation.common.validation.ValidationGroups.*;

public record RoomTypeDto(
        @EnumValid(enumClass = RoomEnum.class, groups = PatternGroup.class)
        @NotNull(message = "값이 없습니다.", groups = NotBlankGroup.class)
        String roomType
) {
}
