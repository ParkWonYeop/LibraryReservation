package com.example.libraryreservation.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.example.libraryreservation.common.validation.ValidationGroups.NotBlankGroup;
import static com.example.libraryreservation.common.validation.ValidationGroups.PatternGroup;


public record RefreshDto(
        @NotBlank(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        @Pattern(regexp = "[0-9]{8,12}", message = "전화번호 형식을 맞춰주세요.", groups= PatternGroup.class)
        String phoneNumber,
        @NotBlank(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        String refreshToken
) {}
