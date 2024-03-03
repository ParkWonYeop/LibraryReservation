package com.example.libraryreservation.auth;

import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.auth.dto.RefreshDto;
import com.example.libraryreservation.auth.dto.SignupDto;
import com.example.libraryreservation.common.model.TokenModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenModel login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@Valid @RequestBody SignupDto signupDto) {
        return authService.signup(signupDto);
    }

    @PutMapping("/token")
    public String refreshToken(@Valid @RequestBody RefreshDto refreshDto) {
        return authService.refreshToken(refreshDto);
    }

    @GetMapping("/token")
    public String checkToken() {
        return authService.checkToken();
    }
}
