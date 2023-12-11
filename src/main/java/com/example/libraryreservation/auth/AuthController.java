package com.example.libraryreservation.auth;

import com.example.libraryreservation.dto.LoginDto;
import com.example.libraryreservation.dto.RefreshDto;
import com.example.libraryreservation.dto.SignupDto;
import com.example.libraryreservation.response.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Message> login(@Valid @RequestBody LoginDto loginDto) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = authService.login(loginDto);

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid @RequestBody SignupDto signupDto) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = authService.signup(signupDto);

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @PutMapping("/token")
    public ResponseEntity<Message> refreshToken(@Valid @RequestBody RefreshDto refreshDto) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = authService.refreshToken(refreshDto);

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }
}
