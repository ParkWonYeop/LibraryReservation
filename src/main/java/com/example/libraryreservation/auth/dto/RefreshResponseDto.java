package com.example.libraryreservation.auth.dto;

public record RefreshResponseDto(
        String accessToken,
        String refreshToken
) {
}
