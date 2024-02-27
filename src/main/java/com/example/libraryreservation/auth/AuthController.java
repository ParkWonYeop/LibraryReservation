package com.example.libraryreservation.auth;

import com.example.libraryreservation.auth.dto.LoginDto;
import com.example.libraryreservation.auth.dto.RefreshDto;
import com.example.libraryreservation.auth.dto.SignupDto;
import com.example.libraryreservation.common.model.TokenModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Transactional
    @PostMapping("/login")
    public TokenModel login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @Transactional
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@Valid @RequestBody SignupDto signupDto) {
        return authService.signup(signupDto);
    }

    @Transactional
    @PutMapping("/token")
    public String refreshToken(@Valid @RequestBody RefreshDto refreshDto) {
        return authService.refreshToken(refreshDto);
    }

    @Transactional(readOnly = true)
    @GetMapping("/token")
    public String checkToken() {
        return authService.checkToken();
    }
}
