package com.example.libraryreservation.admin;

import com.example.libraryreservation.common.controller.LibraryReservationException;
import com.example.libraryreservation.common.controller.constant.CommunalResponse;
import com.example.libraryreservation.common.model.ReservationEntity;
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
    public List<ReservationEntity> getReservationList() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void deleteReservation(long reservationId) {
        Optional<ReservationEntity> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if (reservationModel.isEmpty()) {
            throw new LibraryReservationException(CommunalResponse.RESERVATION_NOT_FOUND);
        }
        reservationRepository.delete(reservationModel.get());
    }
}
