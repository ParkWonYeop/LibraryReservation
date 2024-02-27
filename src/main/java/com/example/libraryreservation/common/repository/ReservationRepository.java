package com.example.libraryreservation.common.repository;

import com.example.libraryreservation.common.enums.RoomEnum;
import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.model.RoomModel;
import com.example.libraryreservation.common.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long > {
    List<ReservationModel> findReservationModelsByUserModel(UserModel userModel);
    Optional<ReservationModel> findReservationModelBySeatNumberAndStartTime(
            RoomModel seatNumber, LocalDateTime startTime
    );

    Optional<ReservationModel> findReservationModelByReservationId(long reservationId);
}
