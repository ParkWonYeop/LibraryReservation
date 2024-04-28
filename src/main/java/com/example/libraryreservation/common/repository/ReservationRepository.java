package com.example.libraryreservation.common.repository;

import com.example.libraryreservation.common.model.ReservationEntity;
import com.example.libraryreservation.common.model.RoomEntity;
import com.example.libraryreservation.common.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findReservationModelsByUserModel(UserEntity userEntity);

    Optional<ReservationEntity> findReservationModelBySeatNumberAndStartTime(
            RoomEntity seatNumber, LocalDateTime startTime
    );

    Optional<ReservationEntity> findReservationModelByReservationId(long reservationId);
}
