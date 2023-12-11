package com.example.libraryreservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RefreshDto {
    @NotBlank
    @Pattern(regexp = "[0-9]{8,12}", message = "전화번호 형식을 맞춰주세요.")
    private String phoneNumber;
    @NotBlank
    private String refreshToken;
}
