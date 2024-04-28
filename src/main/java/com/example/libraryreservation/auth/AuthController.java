package com.example.libraryreservation.auth;

import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.auth.dto.RefreshDto;
import com.example.libraryreservation.auth.dto.RefreshResponseDto;
import com.example.libraryreservation.auth.dto.SignupDto;
import com.example.libraryreservation.common.model.TokenEntity;
import com.example.libraryreservation.common.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenEntity login(@Validated(ValidationSequence.class) @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@Validated(ValidationSequence.class) @RequestBody SignupDto signupDto) {
        authService.signup(signupDto);
    }

    @PutMapping("/token")
    public RefreshResponseDto refreshToken(@Validated(ValidationSequence.class) @RequestBody RefreshDto refreshDto) {
        return authService.refreshToken(refreshDto);
    }

    @GetMapping("/token")
    public void checkToken() {}
}
