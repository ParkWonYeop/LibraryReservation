package com.example.libraryreservation.reservation;

import com.example.libraryreservation.common.dto.ReservationDto;
import com.example.libraryreservation.common.model.ReservationModel;
import com.example.libraryreservation.common.model.RoomModel;
import com.example.libraryreservation.common.model.UserModel;
import com.example.libraryreservation.common.repository.ReservationRepository;
import com.example.libraryreservation.common.repository.RoomRepository;
import com.example.libraryreservation.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.libraryreservation.common.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public String reservationSeat(ReservationDto reservationDto) {
        LocalDateTime startTime = reservationDto.getStartTime();
        LocalDateTime endTime = reservationDto.getEndTime();

        List<RoomModel> roomModelList = roomRepository.findRoomModelByRoomType(reservationDto.getRoomType());
        if (roomModelList.isEmpty()) {
            throw new RuntimeException("Room not found");
        }

        RoomModel roomModel = findSeatNumber(roomModelList, reservationDto.getSeatNumber());

        if(roomModel==null) {
            throw new RuntimeException("SeatNumber not found");
        }

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if(userModelOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserModel userModel = userModelOptional.get();

        List<ReservationModel> reservationList = reservationRepository.findReservationModelsByUserModel(userModel);

        if(!reservationList.isEmpty()) {
            throw new RuntimeException("Already reservation user");
        }

        Optional<ReservationModel> checkReservation = reservationRepository.findReservationModelBySeatNumberAndStartTime(
                roomModel, reservationDto.getStartTime()
        );

        if(checkReservation.isPresent()) {
            throw new RuntimeException("Already reservation seat");
        }

        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setSeatNumber(roomModel);
        reservationModel.setUserModel(userModel);
        reservationModel.setStartTime(startTime);
        reservationModel.setEndTime(endTime);

        reservationRepository.save(reservationModel);

        return "success";
    }

    public List<ReservationModel> getReservationList() {
        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());
        if(userModelOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return reservationRepository.findReservationModelsByUserModel(userModelOptional.get());
    }

    public String deleteReservation(long reservationId) {
        Optional<ReservationModel> reservationModel = reservationRepository.findReservationModelByReservationId(reservationId);
        if(reservationModel.isEmpty()) {
            throw new RuntimeException("Reservation is not found");
        }

        Optional<UserModel> userModelOptional = userRepository.findUserModelByPhoneNumber(getCurrentMemberId());

        if(userModelOptional.isEmpty()) {
            throw new RuntimeException("User is not found");
        }

        if(!Objects.equals(reservationModel.get().getUserModel().getPhoneNumber(), userModelOptional.get().getPhoneNumber())) {
            throw new RuntimeException("User is not correct");
        }

        reservationRepository.delete(reservationModel.get());

        return "success";
    }

    private RoomModel findSeatNumber(List<RoomModel> seatList, Integer seatNumber) {
        for(RoomModel i : seatList) {
            if(Objects.equals(i.getSeatNumber(), seatNumber)) {
                return i;
            }
        }
        return null;
    }
}
