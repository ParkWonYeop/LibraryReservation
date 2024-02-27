package com.example.libraryreservation.admin;

import com.example.libraryreservation.common.model.ReservationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Transactional(readOnly = true)
    @GetMapping("/reservation")
    public List<ReservationModel> getReservationList() {
        return adminService.getReservationList();
    }

    @Transactional
    @DeleteMapping("/reservation")
    public String deleteReservation(@RequestParam(name="id") long reservationId) {
        return adminService.deleteReservation(reservationId);
    }
}
