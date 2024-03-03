package com.example.libraryreservation.admin;

import com.example.libraryreservation.admin.constant.AdminResponse;
import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final ReservationRepository reservationRepository;


    @Transactional(readOnly = true)
    public List<ReservationModel> getReservationList() {
        return reservationRepository.findAll();
    }

    @Transactional
    public String deleteReservation(long reservationId) {
        Optional<ReservationModel> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if (reservationModel.isEmpty()) {
            throw new LibraryReservationException(AdminResponse.NOT_EXIST_RESERVATION);
        }
        reservationRepository.delete(reservationModel.get());
        return "success";
    }
}
