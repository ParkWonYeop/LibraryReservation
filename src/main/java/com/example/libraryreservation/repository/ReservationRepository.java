package com.example.libraryreservation.repository;

import com.example.libraryreservation.model.ReservationModel;
import com.example.libraryreservation.model.RoomModel;
import com.example.libraryreservation.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends MongoRepository<ReservationModel, String > {
    List<ReservationModel> findReservationModelsByUserModel(UserModel userModel);
    Optional<ReservationModel> findReservationModelByRoomModelAndSeatNumberAndStartTime(
            RoomModel roomModel,
            Integer seatNumber,
            LocalDateTime startTime
    );

    Optional<ReservationModel> findReservationModelByReservationId(String reservationId);
}
