package com.example.libraryreservation.auth;

import com.example.libraryreservation.dto.LoginDto;
import com.example.libraryreservation.dto.RefreshDto;
import com.example.libraryreservation.dto.SignupDto;
import com.example.libraryreservation.response.Message;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Message> login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = authService.login(loginDto);

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid @RequestBody SignupDto signupDto, HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = authService.signup(signupDto);

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @PutMapping("/token")
    public ResponseEntity<Message> refreshToken(@Valid @RequestBody RefreshDto refreshDto, HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = authService.refreshToken(refreshDto);
        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @GetMapping("/token")
    public ResponseEntity<Message> checkToken(HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = authService.checkToken();

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }
}
