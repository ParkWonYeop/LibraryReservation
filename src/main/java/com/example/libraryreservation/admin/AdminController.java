package com.example.libraryreservation.admin;

import com.example.libraryreservation.annotation.QueryStringNaming;
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
    public ResponseEntity<Message> getReservationList() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = adminService.getReservationList();
        
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @DeleteMapping("/reservation")
    public ResponseEntity<Message> deleteReservation(@RequestParam(name="id") String reservationId) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = adminService.deleteReservation(reservationId);

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }
}
