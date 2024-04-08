package com.example.libraryreservation.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservationDeleteDto(
        @NotNull(message = "값이 없습니다.")
        @Positive(message = "값이 음수입니다.")
        Long id
) {
}
