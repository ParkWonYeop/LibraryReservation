package com.example.libraryreservation.admin;

import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.repository.ReservationRepository;
import com.example.libraryreservation.common.repository.RoomRepository;
import com.example.libraryreservation.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;


    public List<ReservationModel> getReservationList() {
        return reservationRepository.findAll();
    }

    public String deleteReservation(long reservationId) {
        Optional<ReservationModel> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if(reservationModel.isEmpty()) {
            throw new RuntimeException("reservation is not found");
        }
        reservationRepository.delete(reservationModel.get());
        return "success";
    }
}
