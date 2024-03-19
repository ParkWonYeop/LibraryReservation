package com.example.libraryreservation.admin;

import com.example.libraryreservation.common.model.ReservationModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/reservation")
    public List<ReservationModel> getReservationList() {
        return adminService.getReservationList();
    }

    @DeleteMapping("/reservation")
    public void deleteReservation(@RequestParam(name = "id") long reservationId) {
        adminService.deleteReservation(reservationId);
    }
}
