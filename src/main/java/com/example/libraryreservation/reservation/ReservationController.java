package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.dto.ReservationDto;
import com.example.libraryreservation.common.model.ReservationModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String reservationSeat(@Valid @RequestBody ReservationDto reservationDto) {
        return reservationService.reservationSeat(reservationDto);
    }

    @GetMapping()
    public List<ReservationModel> getReservationList() {
        return reservationService.getReservationList();
    }

    @DeleteMapping()
    public String deleteReservation(@RequestParam(name = "id") long id) {
        return reservationService.deleteReservation(id);
    }
}
