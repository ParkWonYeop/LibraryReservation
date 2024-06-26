package com.example.libraryreservation.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.example.libraryreservation.common.validation.ValidationGroups.NotBlankGroup;
import static com.example.libraryreservation.common.validation.ValidationGroups.PatternGroup;

public record LoginDto(
        @Pattern(regexp = "[0-9]{8,12}", message = "전화번호 형식을 맞춰주세요.", groups = PatternGroup.class)
        @NotBlank(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        String phoneNumber,
        @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.", groups = PatternGroup.class)
        @NotBlank(message = "빈 문자열 입니다.", groups = NotBlankGroup.class)
        String password
) {}