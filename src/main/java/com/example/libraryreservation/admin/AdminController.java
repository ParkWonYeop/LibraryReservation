package com.example.libraryreservation.admin;

import com.example.libraryreservation.common.dto.ReservationDeleteDto;
import com.example.libraryreservation.common.model.ReservationEntity;
import com.example.libraryreservation.common.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/reservation")
    public List<ReservationEntity> getReservationList() {
        return adminService.getReservationList();
    }

    @DeleteMapping("/reservation")
    public void deleteReservation(@Validated(ValidationSequence.class) ReservationDeleteDto reservationDeleteDto) {
        adminService.deleteReservation(reservationDeleteDto.id());
    }
}
