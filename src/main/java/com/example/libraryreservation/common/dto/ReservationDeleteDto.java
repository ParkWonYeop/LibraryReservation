package com.example.libraryreservation.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import static com.example.libraryreservation.common.validation.ValidationGroups.NotBlankGroup;
import static com.example.libraryreservation.common.validation.ValidationGroups.PositiveGroup;

public record ReservationDeleteDto(
        @NotNull(message = "값이 없습니다.", groups = NotBlankGroup.class)
        @Positive(message = "값이 음수입니다.", groups = PositiveGroup.class)
        Long id
) {
}
