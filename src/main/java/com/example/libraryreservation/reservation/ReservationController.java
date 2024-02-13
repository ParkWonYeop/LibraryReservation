package com.example.libraryreservation.reservation;

import com.example.libraryreservation.annotation.QueryStringNaming;
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
    public ResponseEntity<Message> reservationSeat(@Valid @RequestBody ReservationDto reservationDto) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = reservationService.reservationSeat(reservationDto);

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @GetMapping()
    public ResponseEntity<Message> getReservationList() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = reservationService.getReservationList();

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }

    @DeleteMapping()
    public ResponseEntity<Message> deleteReservation(@RequestParam(name = "id") String id) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = reservationService.deleteReservation(id);

        return new ResponseEntity<>(message, headers, message.getStatus().getStatusCode());
    }
}
