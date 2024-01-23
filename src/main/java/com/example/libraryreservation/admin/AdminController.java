package com.example.libraryreservation.admin;

import com.example.libraryreservation.response.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/reservation")
    public ResponseEntity<Message> getReservationList(HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = adminService.getReservationList();
        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<Message> deleteReservation(@RequestParam(name="id") String reservationId, HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = adminService.deleteReservation(reservationId);

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }
}
