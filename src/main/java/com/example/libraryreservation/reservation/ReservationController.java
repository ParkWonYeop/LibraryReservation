package com.example.libraryreservation.reservation;

import com.example.libraryreservation.dto.ReservationDto;
import com.example.libraryreservation.response.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping()
    public ResponseEntity<Message> reservationSeat(@Valid @RequestBody ReservationDto reservationDto, HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = reservationService.reservationSeat(reservationDto);

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @GetMapping()
    public ResponseEntity<Message> getReservationList(HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = reservationService.getReservationList();

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @DeleteMapping()
    public ResponseEntity<Message> deleteReservation(@RequestParam(name="id") String id, HttpServletRequest request) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = reservationService.deleteReservation(id);

        log.info("Response. IP: {"+request.getRemoteAddr()+"}, URI: {"+request.getRequestURI()+"}, status: {"+message.getStatus()+"}, message: {"+message.getMessage()+"}, dataType: {"+ message.getData().getClass().getName()+"}");
        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }
}
