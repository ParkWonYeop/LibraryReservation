package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.dto.ReservationDeleteDto;
import com.example.libraryreservation.common.dto.ReservationDto;
import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void reservationSeat(@Validated(ValidationSequence.class) @RequestBody ReservationDto reservationDto) {
        reservationService.reservationSeat(reservationDto);
    }

    @GetMapping()
    public List<ReservationModel> getReservationList() {
        return reservationService.getReservationList();
    }

    @DeleteMapping()
    public void deleteReservation(@Validated(ValidationSequence.class) ReservationDeleteDto reservationDeleteDto) {
        reservationService.deleteReservation(reservationDeleteDto.id());
    }
}
